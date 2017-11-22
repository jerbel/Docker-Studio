/**
 * Copyright (c) 2016-2017 Inria
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * - Christophe Gourdin <christophe.gourdin@inria.fr>
 *  
 */
package org.eclipse.cmf.occi.docker.connector.observer;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.cmf.occi.core.Resource;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

/**
 * This class notifies monitoring events from the connector.
 * 
 * @author cgourdin
 *
 */
public class StatsCallBack extends ResultCallbackTemplate<StatsCallBack, Statistics> {
	private static Logger LOGGER = LoggerFactory.getLogger(StatsCallBack.class);

	private List<Statistics> statisticsList = new LinkedList<>();

	private String containerId;

	private Container container;

	private LimitedQueue<Float> cpuTotalUsageQueue = new LimitedQueue<Float>(2);

	private LimitedQueue<Float> cpuSystemUsageQueue = new LimitedQueue<Float>(2);

	private boolean updateMaxCpu = false;

	public StatsCallBack(String containerId) {
		this.containerId = containerId;
	}

	public StatsCallBack(Container container) {
		this.container = container;
		// this.containerId = container.getContainerid();
	}

	@Override
	public void onNext(Statistics stats) {
		// System.out.println("Received stats #{} :: {} :: {}", statisticsList.size(),
		// this.container.containerid, stats)
		// Interval in which the metrics are retrieved
		try {
			Thread.sleep(this.container.getMonitoringInterval()); // Pause for x ms
		} catch (InterruptedException ex) {
			// No op.
		}
		statisticsList.add(stats);
		// Calculate the percentage of CPU used
		Map<String, Object> cpu = stats.getCpuStats();

		LinkedHashMap<String, Object> tmpcpu = (LinkedHashMap<String, Object>) cpu.get("cpu_usage");
		Object cpu_used = tmpcpu.get("total_usage");
		Object percpu_usage = tmpcpu.get("percpu_usage");
		Object system_cpu_usage = cpu.get("system_cpu_usage");
		Object percpu_usage_size = (List<Object>) percpu_usage;
		// Memory.
		Integer mem_used = (Integer) stats.getMemoryStats().get("usage");
		Long mem_limit = (Long) stats.getMemoryStats().get("limit");
		Map<String, Object> networks = stats.getNetworks();
		Map<String, Object> network = stats.getNetwork();
		Integer network_r = null;
		Integer network_t = null;
		Integer bandwitdh = null;
		try {
			if (networks != null) {
				LinkedHashMap<String, Object> tmpnetworks = (LinkedHashMap<String, Object>) networks.get("eth0");
				// System.out.println("Networks : {}", tmpnetworks)
				network_r = (Integer) tmpnetworks.get("rx_bytes");
				network_t = (Integer) tmpnetworks.get("tx_bytes");
				bandwitdh = network_r + network_t;

			} else {
				network_r = (Integer) network.get("rx_bytes");
				network_t = (Integer) network.get("tx_bytes");
				bandwitdh = network_r + network_t;
			}

		} catch (Exception e) {
			network_r = 0;
			network_t = 0;
			bandwitdh = 0;
			LOGGER.error(e.getMessage());
		}

		// Update the Queue
		cpuTotalUsageQueue.add(Float.valueOf(cpu_used.toString()));
		cpuSystemUsageQueue.add(Float.valueOf(system_cpu_usage.toString()));

		if (cpuTotalUsageQueue.size() == 2 && cpuSystemUsageQueue.size() == 2) {
			// Calculate the percentage
			Float percent = calculateCPUPercent(cpuTotalUsageQueue, cpuSystemUsageQueue,
					((List<Statistics>) percpu_usage_size).size());
			// Update the monitoring metrics
			try {
				modifyResourceSet(this.container, cpu_used.toString(), percent, mem_used, mem_limit, bandwitdh,
						((List<Statistics>) percpu_usage_size).size(), updateMaxCpu);
				updateMaxCpu = true;
			} catch (NullPointerException e) {
				LOGGER.error(e.getMessage());
			}
		}

	}

	/**
	 * Update resource model.
	 * 
	 * @param resource
	 * @param cpu_used
	 * @param percent
	 * @param mem_used
	 * @param mem_limit
	 * @param bandwitdh
	 * @param cpuMax
	 * @param updateMaxCpu
	 */
	public synchronized void modifyResourceSet(Resource resource, String cpu_used, Float percent, Integer mem_used,
			Long mem_limit, Integer bandwitdh, Integer cpuMax, Boolean updateMaxCpu) {

		// Creating an editing domain
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(resource.eResource().getResourceSet());

		Command cmd = new RecordingCommand(domain) {

			@Override
			protected void doExecute() {
				// these modifications require a write transaction in this editing domain
				DecimalFormat df = new DecimalFormat("#0.##");
				int cpu_max = 0;
				Float cpu_us = 0.0F;
				Integer memLimitInt = Integer.parseInt("" + (mem_limit / 1024));
				Integer memUsedInt = (mem_used / 1024);

				Float mem_percent = Float.parseFloat("" + memUsedInt) / Float.parseFloat("" + memLimitInt);
				try {
					Thread.sleep(100);
					// Modify the resource only if it is in active state
					if (resource instanceof Container) {
						Container lookContainer = (Container) resource;

						if (((Compute) resource).getOcciComputeState() == ComputeStatus.ACTIVE) {

							// Update Attributes only if change occurs
							if (lookContainer.getMemoryUsed() != memUsedInt) {
								lookContainer.setMemoryUsed(memUsedInt);
							}
							if (lookContainer.getMemoryMaxValue() != memLimitInt) {
								lookContainer.setMemoryMaxValue(memLimitInt);
							}
							if (lookContainer.getMemoryPercent() != df.format(mem_percent)) {
								lookContainer.setMemoryPercent(df.format(mem_percent));
							}
							if (lookContainer.getBandwidthUsed() != bandwitdh) {
								lookContainer.setBandwidthUsed(bandwitdh);
							}
							cpu_us = (Float.valueOf("" + cpu_used)) / 1000000F;

							// To avoid NumberFormatException, the maximum value of Integer is 2^31-1 =
							// 2147483647
							if (cpu_us.intValue() > Integer.MAX_VALUE) {
								cpu_us = cpu_us / 100000000F;
							}

							cpu_max = getMaxValue(Long.parseLong("" + cpu_us.intValue()), percent);

							if (cpu_max > Integer.MAX_VALUE) {
								cpu_max = cpu_max / 100000000;
								cpu_us = cpu_us / 100000000F;
							}

							if (lookContainer.getCpuUsed() != cpu_us.intValue()) {
								lookContainer.setCpuUsed(cpu_us.intValue());
								// System.out.println("CPU USED <=====> {}", cpu_us.intValue)
							}
							if (lookContainer.getCpuMaxValue() != Integer.valueOf(cpu_max)) {
								lookContainer.setCpuMaxValue(cpu_max);
								// System.out.println("CPU MAX VALUE <=====> {}", Integer.valueOf(cpu_max))
							}
							if (lookContainer.getCpuPercent() != df.format(percent)) {
								lookContainer.setCpuPercent(df.format(percent));
								// System.out.println("CPU PERCENTAGE <=====> {}", percent)
							}

							// Update the number of cores once
							if (!updateMaxCpu) {
								lookContainer.setCoreMax(cpuMax);
								// updateMaxCpu = true;
							}
						}
					}
				} catch (NumberFormatException e) {
					LOGGER.error(e.getMessage());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		try {
			((TransactionalCommandStack) domain.getCommandStack()).execute(cmd, null); // default options
		} catch (RollbackException rbe) {
			LOGGER.error(rbe.getStatus().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getContainerId() {
		return this.containerId;
	}

	public List<Statistics> getStatisticsList() {
		return this.statisticsList;
	}

	public Boolean compateTo(Statistics stats1, Statistics stats2) {
		return stats1.toString().equals(stats2.toString());
	}

	/**
	 * 
	 * @param cpuTotalUsageQueue
	 * @param cpuSystemUsageQueue
	 * @param percpu_usage_size
	 * @return
	 */
	public Float calculateCPUPercent(LimitedQueue<Float> cpuTotalUsageQueue, LimitedQueue<Float> cpuSystemUsageQueue,
			int percpu_usage_size) {
		// Inspired from
		// https://github.com/docker/docker/blob/0d445685b8d628a938790e50517f3fb949b300e0/api/client/stats.go#L199
		Float cpuPercent = 0.0F;
		// calculate the change for the cpu usage of the container in between readings
		Float cpuDelta = cpuTotalUsageQueue.get(1) - cpuTotalUsageQueue.get(0);
		// calculate the change for the entire system between readings
		Float systemDelta = cpuSystemUsageQueue.get(1) - cpuSystemUsageQueue.get(0);

		if (systemDelta > 0.0 && cpuDelta > 0.0) {
			cpuPercent = (cpuDelta / systemDelta) * percpu_usage_size * 100.0F;
		}
		return cpuPercent;
	}

	/**
	 * 
	 * @param cpu_used
	 * @param percent
	 * @return
	 */
	public int getMaxValue(Long cpu_used, Float percent) {
		Float value = (100F * cpu_used.floatValue()) / percent;
		int maxValue = value.intValue();
		return maxValue;
	}

}

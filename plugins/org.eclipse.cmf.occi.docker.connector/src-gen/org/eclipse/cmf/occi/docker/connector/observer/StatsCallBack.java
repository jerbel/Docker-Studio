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

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.cmf.occi.core.Resource;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.connector.ContainerConnector;
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

	private Container container;

	private LimitedQueue<Float> cpuTotalUsageQueue = new LimitedQueue<Float>(2);

	private LimitedQueue<Float> cpuSystemUsageQueue = new LimitedQueue<Float>(2);

	private boolean updateMaxCpu = false;
	
	/**
	 * Monitoring interval to get values between period.
	 */
	private Integer monitoringInterval = 2000;


	public StatsCallBack(Container container) {
		this.container = container;
	}

	@Override
	public void onNext(Statistics stats) {
		
		// Interval in which the metrics are retrieved
		try {
			if (this.container == null) {
				System.err.println("The container is not set correctly on stats call back !!!");
				return;
			}
			if (this.container.getMonitoringInterval() != null) {
				monitoringInterval = this.container.getMonitoringInterval(); // Else default is 2000 ms.
			}
			Thread.sleep(monitoringInterval); // Pause for x ms
		} catch (InterruptedException ex) {
			// No op.
		}
		if (this.container.getOcciComputeState().equals(ComputeStatus.INACTIVE)) {
			System.out.println("Container : " + container.getName() + " is inactive");
			try {
				this.close();
				((ContainerConnector)container).setStatsCallBack(new StatsCallBack(container));
				
			} catch (IOException ex) {
				ex.printStackTrace();
				System.err.println("Exception thrown IOException : " + ex.getMessage());
			}
			return;
		}
		// Calculate the percentage of CPU used
		Map<String, Object> cpu = stats.getCpuStats();

		LinkedHashMap<String, Object> tmpcpu = (LinkedHashMap<String, Object>) cpu.get("cpu_usage");
		Object cpuUsed = tmpcpu.get("total_usage");
		Object percpuUsage = tmpcpu.get("percpu_usage");
		Object systemCpuUsage = cpu.get("system_cpu_usage");
		Object percpuUsageSize = (List<Object>) percpuUsage;
		// Memory.
		Integer memUsed = (Integer) stats.getMemoryStats().get("usage");
		System.out.println("mem used : " + memUsed);
		
		Object memLimitObj = stats.getMemoryStats().get("limit");
		// Parse to long the memLimitObj
		Long memLimit = Long.valueOf(memLimitObj.toString());
		System.out.println("mem limit: " + memLimit);
		
		Map<String, Object> networks = stats.getNetworks();
		// Map<String, Object> network = stats.getNetwork();
		Integer networkR = null;
		Integer networkT = null;
		Integer bandwitdh = null;
		try {
			if (networks != null) {
				LinkedHashMap<String, Object> tmpnetworks = (LinkedHashMap<String, Object>) networks.get("eth0");
				// System.out.println("Networks : {}", tmpnetworks)
				networkR = (Integer) tmpnetworks.get("rx_bytes");
				networkT = (Integer) tmpnetworks.get("tx_bytes");
				bandwitdh = networkR + networkT;
			}

		} catch (Exception e) {
			e.printStackTrace();
			networkR = 0;
			networkT = 0;
			bandwitdh = 0;
			LOGGER.error("Networks bandwidth -> Exception thrown : " + e.getClass().getName() + " --> " + e.getMessage());
		}

		// Update the Queue
		cpuTotalUsageQueue.add(Float.valueOf(cpuUsed.toString()));
		cpuSystemUsageQueue.add(Float.valueOf(systemCpuUsage.toString()));

		if (cpuTotalUsageQueue.size() == 2 && cpuSystemUsageQueue.size() == 2) {
			// Calculate the percentage
			Float percent = calculateCPUPercent(cpuTotalUsageQueue, cpuSystemUsageQueue,
					((List<Statistics>) percpuUsageSize).size());
			// Update the monitoring metrics
			try {
				System.out.println("Container : " + this.container.getContainerid());
				modifyResourceSet(this.container, cpuUsed.toString(), percent, memUsed, memLimit, bandwitdh,
						((List<Statistics>) percpuUsageSize).size(), updateMaxCpu);
				updateMaxCpu = true;
			} catch (Exception e) {
				LOGGER.error("Exception thrown : " + e.getClass().getName() + " --> " + e.getMessage());
			}
		}

	}

	/**
	 * Update resource model.
	 * 
	 * @param resource
	 * @param cpuUsed
	 * @param percent
	 * @param memUsed
	 * @param memLimit
	 * @param bandwitdh
	 * @param cpuMax
	 * @param updateMaxCpu
	 */
	public synchronized void modifyResourceSet(Resource resource, final String cpuUsed, final Float percent, final Integer memUsed,
			final Long memLimit, final Integer bandwitdh, final Integer cpuMax, final Boolean updateMaxCpu) {
		// For debug...
		System.out.println("In modify resource set !");
		if (cpuUsed == null) {
			System.out.println("Cpu used is null");
		}
		if (percent == null) {
			System.out.println("CPU percent is null");
		}
		if (memUsed == null) {
			System.out.println("Mem used is null");
		}
		if (memLimit == null) {
			System.out.println("Mem limit is null");
		}
		if (bandwitdh == null) {
			System.out.println("Bandwidth is null");
		}
		if (cpuMax == null) {
			System.out.println("CPU Max is null");
		}
		if (updateMaxCpu == null) {
			System.out.println("update max CPU is null, it must be a boolean");
		}
		
		// Get an editing domain
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(resource.eResource().getResourceSet());

		Command cmd = new RecordingCommand(domain) {

			@Override
			protected void doExecute() {
			
				try {
					// these modifications require a write transaction in this editing domain
					DecimalFormat df = new DecimalFormat("#0.##");
					int cpuMax = 0;
					Float cpuUs = 0.0F;
					Integer memLimitInt = Integer.parseInt("" + (memLimit / 1024));
					Integer memUsedInt = (memUsed / 1024);

					Float memPercent = Float.parseFloat("" + memUsedInt) / Float.parseFloat("" + memLimitInt);
					Thread.sleep(100);
					
					// Modify the resource only if it is in active state
					if (resource instanceof Container) {
						Container container = (Container) resource;

						if (((Compute) resource).getOcciComputeState() == ComputeStatus.ACTIVE) {
							Integer memContainerUsed = container.getMemoryUsed();
							Integer memContainerMaxValue = container.getMemoryMaxValue();
							String memContainerMemoryPercent = container.getMemoryPercent();
							String bandwidthContainerPercent = container.getBandwidthPercent();
							Integer bandwdthContainerUsed = container.getBandwidthUsed();
							Integer cpuContainerMaxValue = container.getCpuMaxValue();
							Integer cpuContainerUsed = container.getCpuUsed();
							String cpuContainerPercent = container.getCpuPercent();
							
							if (memContainerUsed == null) {
								memContainerUsed = 0;
							}
							if (memContainerMemoryPercent == null) {
								memContainerMemoryPercent = df.format(0.0F);
							}
							if (memContainerMaxValue == null) {
								memContainerMaxValue = 0;
							}
							if (bandwdthContainerUsed == null) {
								bandwdthContainerUsed = 0;
							}
							if (bandwidthContainerPercent == null) {
								bandwidthContainerPercent = df.format(0.0F);
							}
							if (cpuContainerUsed == null) {
								cpuContainerUsed = 0;
							}
							if (cpuContainerMaxValue == null) {
								cpuContainerMaxValue = 0;
							}
							if (cpuContainerPercent == null) {
								cpuContainerPercent = df.format(0.0F);
							}
							
							// Update Attributes only if change occurs
							if (memContainerUsed != memUsedInt) {
								container.setMemoryUsed(memUsedInt);
							}
							if (memContainerMaxValue != memLimitInt) {
								container.setMemoryMaxValue(memLimitInt);
							}
							if (memContainerMemoryPercent != df.format(memPercent)) {
								container.setMemoryPercent(df.format(memPercent));
							}
							if (bandwdthContainerUsed != bandwitdh) {
								container.setBandwidthUsed(bandwitdh);
							}
							
							cpuUs = (Float.valueOf("" + cpuUsed)) / 1000000F;

							// To avoid NumberFormatException, the maximum value of Integer is 2^31-1 =
							// 2147483647
							if (cpuUs.intValue() > Integer.MAX_VALUE) {
								cpuUs = cpuUs / 100000000F;
							}

							cpuMax = getMaxValue(Long.parseLong("" + cpuUs.intValue()), percent);

							if (cpuMax > Integer.MAX_VALUE) {
								cpuMax = cpuMax / 100000000;
								cpuUs = cpuUs / 100000000F;
							}

							if (cpuContainerUsed != cpuUs.intValue()) {
								container.setCpuUsed(cpuUs.intValue());
								// System.out.println("CPU USED <=====> {}", cpu_us.intValue)
							}
							if (cpuContainerMaxValue != Integer.valueOf(cpuMax)) {
								container.setCpuMaxValue(cpuMax);
								// System.out.println("CPU MAX VALUE <=====> {}", Integer.valueOf(cpu_max))
							}
							if (!cpuContainerPercent.equals(df.format(percent))) {
								container.setCpuPercent(df.format(percent));
								// System.out.println("CPU PERCENTAGE <=====> {}", percent)
							}

							// Update the number of cores once
							if (!updateMaxCpu) {
								container.setCoreMax(cpuMax);
								// updateMaxCpu = true;
							}
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					System.err.println("NumberFormatException : " + e.getMessage());
					LOGGER.error(e.getMessage());
				} catch (InterruptedException e) {
					// No op.
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
	 * Calculate the cpu max value.
	 * @param cpuUsed
	 * @param percent
	 * @return 
	 */
	public int getMaxValue(Long cpuUsed, Float percent) {
		Float value = (100F * cpuUsed.floatValue()) / percent;
		int maxValue = value.intValue();
		return maxValue;
	}

}

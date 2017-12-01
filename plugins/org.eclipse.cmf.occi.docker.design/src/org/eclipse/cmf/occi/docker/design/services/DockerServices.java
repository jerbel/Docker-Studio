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
package org.eclipse.cmf.occi.docker.design.services;

import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.ContainerConnector;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerModelHelper;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EObject;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.cmf.occi.core.Configuration;
import org.eclipse.cmf.occi.core.Entity;
import org.eclipse.cmf.occi.core.Link;
import org.eclipse.cmf.occi.docker.Network;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.RestartMethod;
import org.eclipse.cmf.occi.infrastructure.StopMethod;

/**
 * 
 * @author Christophe Gourdin
 *
 */
public class DockerServices {

	/**
	 * Popup menu Start action.
	 */
	public void start(final EObject eo) {
		executeCommand(eo, "start", false);
	}

	/**
	 * Popup menu importModel action.
	 */
	public void importModel(final Configuration conf) {
		try {
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					DockerModelHelper dockerModelHelper = new DockerModelHelper(conf);
					try {

						dockerModelHelper.importModel();
					} catch (DockerException ex) {
						ex.printStackTrace();
					}
				}
			};
			Shell shell = this.getShell();
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
			dialog.run(false, true, runnable);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Popup menu StartAll action
	 */
	public void startAll(final EObject eo) {
		executeCommand(eo, "startall", false);
	}

	/**
	 * Popup menu stop action.
	 */
	public void stop(final EObject eo) {
		executeCommand(eo, "stop", false);
	}

	/**
	 * Popup menu restart action.
	 */
	public void restart(final EObject eo) {
		executeCommand(eo, "restart", false);
	}

	public void occiCreate(final EObject eo) {
		executeCommand(eo, "occiCreate", true);
	}

	public void occiRetrieve(final EObject eo) {
		executeCommand(eo, "occiRetrieve", true);
	}

	public void occiUpdate(final EObject eo) {
		executeCommand(eo, "occiUpdate", true);
	}

	public void occiDelete(final EObject eo) {
		executeCommand(eo, "occiDelete", true);
	}

	/**
	 * Called by all basic command (not by import command).
	 * 
	 * @param eo
	 * @param command
	 * @param occiCommand
	 */
	private void executeCommand(final EObject eo, final String command, final boolean occiCommand) {
		Shell shell = this.getShell();
		try {
			final int kind = this.eobjectKind(eo);

			String machineName = null;
			final Compute compute;
			final Entity entity;
			if (command == null) {
				MessageDialog.openInformation(shell, "Warning", "This command : " + command + " is not supported.");
				return;
			}

			switch (kind) {
			case 0:
				Machine machine = ((Machine) eo);
				machineName = machine.getName();
				if (machineName == null || machineName.trim().isEmpty()) {
					MessageDialog.openInformation(shell, "Warning", "Machine name is required !");
					return;
				}
				compute = (Compute) eo;
				break;
			case 1:
				Container container = ((Container) eo);
				machineName = container.getName();
				if (machineName == null || machineName.trim().isEmpty()) {
					MessageDialog.openInformation(shell, "Warning", "Container name is required !");
					return;
				}
				compute = (Container) eo;
				break;
			default:
				if (!occiCommand) {
					MessageDialog.openInformation(shell, "Warning", "Unsupported compute !");
					return;
				}
				compute = null;
				break;
			}
			if (eo instanceof Entity) {
				entity = (Entity) eo;
			} else {
				System.err.println("Not an entity !");
				return;
			}
			
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					if (!monitor.isCanceled()) {
						monitor.beginTask("Operation in progress : " + command, Monitor.UNKNOWN);
						Display display = Display.getCurrent();
						boolean result = display.readAndDispatch();
						System.out.println("result display read and dispatch : " + result);
						SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
						// consume..
						subMonitor.worked(10);
						if (occiCommand) {
							switch (command) {
							case "occiCreate":
								entity.occiCreate();
								break;
							case "occiUpdate":
								entity.occiUpdate();
								break;
							case "occiDelete":
								boolean confirm = showConfirmDialog();
								if (confirm) {
									entity.occiDelete();
								}
								break;
							case "occiRetrieve":
								entity.occiRetrieve();
								break;
							}
						} else {
							if (compute != null) {

								switch (command) {

								case "start":
									compute.start();
									break;
								case "startall":
									if (compute instanceof Machine) {
										Machine machine = (Machine) compute;
										machine.startall();
									}

									break;
								case "stop":
									compute.stop(StopMethod.GRACEFUL);
									break;
								case "synchronize":
									compute.occiRetrieve();
									break;
								case "restart":
									compute.restart(RestartMethod.GRACEFUL);
									break;
								default:
									System.err.println("command : " + command + " is not supported.");
									break;
								}
							}
						}
						result = display.readAndDispatch();
						System.out.println("result display read and dispatch : " + result);
						subMonitor.worked(100);
						monitor.done();
					} else {
						System.out.println("Operation : " + command + " cancelled.");
					}

				}
			};

			ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
			dialog.setOpenOnRun(true);

			dialog.run(false, true, runnable);
			//  dialog.run(false, true, runnable);
			// MessageDialog.openInformation(shell, "Operation succeed",
			//		"Command " + command + " has been successfully executed !");
		} catch (Throwable ex) {
			ex.printStackTrace();
			MessageDialog.openError(shell, "Error on command " + command,
					ex.getCause().getClass().getName() + " --> " + ex.getCause().getMessage());
		}

	}

	/**
	 * Popup menu create action.
	 */
	public void create(final EObject eo) {
		if ((eo instanceof Network)) {
		}
	}

	public Shell getShell() {
		Display current = Display.getCurrent();
		return current.getActiveShell();
	}

	/**
	 * Get the current machine
	 */
	public Machine getMachine(final EObject eo) {
		final int kind = this.eobjectKind(eo);
		if ((kind == 1)) {
			return (Machine) ((ContainerConnector) eo).getCompute();
		}
		return null;
	}

	/**
	 * Classify the EObject according to its kind (Machine/Container)
	 */
	public int eobjectKind(final EObject eo) {
		int kind = 2;
		if ((eo instanceof Machine)) {
			kind = 0;
		} else {
			if ((eo instanceof Container)) {
				kind = 1;
			}
		}
		return kind;
	}
	/**
	 * 
	 * @return
	 */
	public boolean showConfirmDialog() {
		boolean result = false;
		Shell shell = getShell();
		result = MessageDialog.openConfirm(shell, "Confirm", "Please confirm the action");
		return result;
	}
	
	/**
	 * Remove contains is called by design to remove hidden link between a Machine and a container, this method is called before removing container model.
	 * @param eo
	 */
	public void removeContains(final EObject eo) {
		System.out.println("In service method removeContains !");
		if (eo instanceof Container) {
			Container container = (Container)eo;
			String name = ((Container)eo).getName();
			System.out.println("Removing contains for Container : " + name);
			Compute machine = ((ContainerConnector)container).getCompute();
			Link linkToRemove = null;
			for (Link link : machine.getLinks()) {
				if (link.getTarget() != null && link.getTarget().equals(container) && link instanceof Contains) {
					System.out.println("Remove contains :" + link.getId());
					linkToRemove = link;
					break;
				}
			}
			if (linkToRemove != null) {
				machine.getLinks().remove(linkToRemove);
			} else {
				System.err.println("Contains for container : " + name + " doesnt exist !");
			}
			
		} else {
			System.out.println("Cant remove contains, this is not a container !");
		}
	}

}

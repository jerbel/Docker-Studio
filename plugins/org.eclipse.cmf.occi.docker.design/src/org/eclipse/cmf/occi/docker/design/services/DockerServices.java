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
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.ContainerConnector;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerModelHelper;
import org.eclipse.emf.ecore.EObject;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.cmf.occi.core.Configuration;
import org.eclipse.cmf.occi.docker.Network;

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
		try {
			final int kind = this.eobjectKind(eo);
			Shell shell = this.getShell();
			String machineName = null;
			
			if ((kind == 0)) {
				
				Machine machine = ((Machine) eo);
				machineName = machine.getName();
				if (machineName == null || machineName.trim().isEmpty()) {
					MessageDialog.openInformation(shell, "Warning", "Machine name is required !");
					return;
				} else {
					IRunnableWithProgress runnable = new IRunnableWithProgress() {
						@Override
						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							Machine machine = ((Machine) eo);
							machine.start();
						}
					};
					
					ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
					dialog.run(false, true, runnable);
				}
				
			} else if ((kind == 1)) {
					Container container = ((Container) eo);
					machineName = container.getName();
					
					if (machineName == null || machineName.trim().isEmpty()) { 
						MessageDialog.openInformation(shell, "Warning", "Container name is required !");
						return;
					} else {
						IRunnableWithProgress runnable = new IRunnableWithProgress() {
							@Override
							public void run(final IProgressMonitor monitor)
									throws InvocationTargetException, InterruptedException {
								Container container = ((Container) eo);
								container.start();
							}
						};
						ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
						dialog.run(false, true, runnable);
					}
			} else {
				MessageDialog.openInformation(shell, "Warning", "Unsupported compute !");
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
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
	 * Popum menu synchronize action.
	 */
	public void synchronize(final EObject eo) {
		try {
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					if ((eo instanceof Machine)) {
						Machine machine = ((Machine) eo);
						machine.occiRetrieve();
					}
				}
			};
			Shell _shell = this.getShell();
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(_shell);
			dialog.run(false, true, runnable);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Popup menu StartAll action
	 */
	public void startAll(final EObject eo) {
		try {
			final int kind = this.eobjectKind(eo);
			
			if ((kind == 0)) {
				Machine machine = ((Machine) eo);
				String machineName = null;
				machineName = machine.getName();
				Shell shell = this.getShell();
				if (machineName == null || machineName.trim().isEmpty()) {
					MessageDialog.openInformation(shell, "Warning", "Machine name is required !");		
				} else {
					IRunnableWithProgress runnable = new IRunnableWithProgress() {
						@Override
						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							Machine machine = ((Machine) eo);
							machine.startall();
						}
					};
					
					ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
					dialog.run(false, true, runnable);
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Popup menu stop action.
	 */
	public void stop(final EObject eo) {
		try {
			final int kind = this.eobjectKind(eo);
			String machineName = null;
			Shell shell = this.getShell();
			if ((kind == 0)) {
				Machine machine = ((Machine) eo);
				machineName = machine.getName();
				if (machineName == null || machineName.trim().isEmpty()) {
					MessageDialog.openInformation(shell, "Warning", "Machine name is required !");
				} else {
					IRunnableWithProgress runnable = new IRunnableWithProgress() {
						@Override
						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							Machine machine = ((Machine) eo);
							machine.stop(StopMethod.GRACEFUL);
						}
					};
					ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
					dialog.run(false, true, runnable);
				}
			} else if ((kind == 1)) {
					Container container = ((Container) eo);
					machineName = container.getName();
					if (machineName == null || machineName.trim().isEmpty()) {
						MessageDialog.openInformation(shell, "Warning", "Container name is required !");
					} else {
						IRunnableWithProgress runnable = new IRunnableWithProgress() {
							@Override
							public void run(final IProgressMonitor monitor)
									throws InvocationTargetException, InterruptedException {
								Container container = ((Container) eo);
								container.stop(StopMethod.GRACEFUL);
							}
						};
						ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
						dialog.run(false, true, runnable);
					}
				}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Popup menu restart action.
	 */
	public void restart(final EObject eo) {
		try {
			final int kind = this.eobjectKind(eo);
			String machineName = null;
			Shell shell = this.getShell();
			if ((kind == 0)) {
				Machine machine = ((Machine) eo);
				machineName = machine.getName();
				
				if (machineName == null || machineName.trim().isEmpty()) {
					MessageDialog.openInformation(shell, "Warning", "Machine name is required!");
				} else {
					IRunnableWithProgress runnable = new IRunnableWithProgress() {
						@Override
						public void run(final IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException {
							Machine machine = ((Machine) eo);
							machine.restart(RestartMethod.GRACEFUL);
						}
					};
					
					ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
					dialog.run(false, true, runnable);
				}
			} else 
				if ((kind == 1)) {
					Container container = ((Container) eo);
					machineName = container.getName();
					if (machineName == null || machineName.trim().isEmpty()) {
						MessageDialog.openInformation(shell, "Warning", "Container name is required!");
					} else {
						IRunnableWithProgress runnable = new IRunnableWithProgress() {
							@Override
							public void run(final IProgressMonitor monitor)
									throws InvocationTargetException, InterruptedException {
								Container container = ((Container) eo);
								container.restart(RestartMethod.GRACEFUL);
							}
						};
						ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
						dialog.run(false, true, runnable);
					}
				}
		} catch (Throwable ex) {
			ex.printStackTrace();
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
			return (Machine)((ContainerConnector) eo).getCompute();
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

	
}

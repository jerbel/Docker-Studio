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
package org.eclipse.cmf.occi.docker.connector.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;

/**
 * Manage commands executed on local shell.
 * 
 * @author Christophe Gourdin
 *
 */
public class ProcessManager {
	private static String OS = DockerMachineCommandFactory.getOS();

	/**
	 * Execute a shell command on system.
	 * 
	 * @param command
	 * @param r
	 *            Runtime object
	 * @return runtime execution result string.
	 * @throws DockerException
	 */
	public static String getOutputCommand(final String command, Runtime r) throws DockerException {
		StringBuffer result = new StringBuffer();
		String[] env;

		if (OS.equalsIgnoreCase("osx") || OS.equalsIgnoreCase("uni")) {
			env = new String[] { "/bin/bash", "-c", command };

		} else if (OS.equalsIgnoreCase("win")) {
			env = new String[] { "cmd", "/c", command };

		} else if (OS.equalsIgnoreCase("sol")) {
			// For solaris
			env = new String[] { "/usr/bin/bash", "-c", command };
		} else {
			throw new DockerException("Cannot initialize the output docker-machine, the OS is undefined : " + OS);
		}
		// For debug...
		System.out.println("Command to exec: ");
		for (String envTmp : env) {
			System.out.println(envTmp);
		}
		try {
			Process process = r.exec(env);
			InputStreamReader input = new InputStreamReader(process.getInputStream());
			BufferedReader reader = new BufferedReader(input);
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line + "\n");
			}
			reader.close();
		} catch (IOException e) {
			// TODO : Check this one... Why this may throw an exception ? Is process running
			// or stopped ?
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * Execute a command and output on systout if output property is true. Wait
	 * until command has been successfully executed.
	 * 
	 * @param command
	 *            the command to execute.
	 * @param r
	 *            runtime object
	 * @param output
	 *            (true : default to system.out, false: no system.out)
	 * @return true if command ok, false if command output has an error.
	 */
	public static boolean runCommand(final String command, Runtime r, final boolean output) throws DockerException {
		try {
			Process process = r.exec(command);
			if (output) {
				new StreamHandler(process.getInputStream(), System.out);

			} else {
				new StreamHandler(process.getInputStream(), null);
				new StreamHandler(process.getErrorStream(), null);
			}
			new StreamHandler(process.getErrorStream(), System.err); // TODO : Remove this and log to output error
																		// logger instead.

			// TODO : Check this, waitFor wait thread until the end of the process (using
			// future ???).
			int result = process.waitFor();

			Output.flushErr();
			Output.flushOut();

			return result == 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DockerException(e.getMessage(), e);

		}

	}

	private static class StreamHandler {
		BufferedReader reader;
		PrintWriter writer;

		public StreamHandler(InputStream inputStream, OutputStream outputStream) {
			reader = new BufferedReader(new InputStreamReader(inputStream));

			if (outputStream != null) {
				writer = new PrintWriter(outputStream);
			}

			handle();
		}

		/**
		 * Handle streams reader and flush after read.
		 */
		public void handle() {
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					String message = null;

					while (true) {

						try {
							message = reader.readLine();
						} catch (IOException ex) {
							ex.printStackTrace();
						}

						if (message == null) {
							return;
						}
						if (writer != null) {
							writer.println(message);
							writer.flush();
						}

					}
				}
			};
			try {
				Thread thread = new Thread(runnable);
				thread.start();
			} catch (Exception e) {
				writer = null;
				reader = null;
			}
		}
	}

	/**
	 * For testing purpose. To remove later.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String command = " docker-machine ls";
		try {
			String p = ProcessManager.getOutputCommand(command, Runtime.getRuntime());
			System.out.println(p);
		} catch (DockerException ex) {
			ex.printStackTrace();
		}

	}

}

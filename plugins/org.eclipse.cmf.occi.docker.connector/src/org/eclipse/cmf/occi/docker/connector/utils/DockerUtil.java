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
package org.eclipse.cmf.occi.docker.connector.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerMachineHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;

import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.MapExtensions;

public class DockerUtil {
	public final static String HOST_RUNNING = "Running";
	 protected static String OS = System.getProperty("os.name").toLowerCase();
	  
	 private static Logger LOGGER = LoggerFactory.getLogger(DockerUtil.class);
	  
	  private static String DOCKER_MACHINE_MACOS = "/usr/local/bin/docker-machine";
	  
	  /**
	   * Run the good docker-machine according to the OS.
	   */
	  public static String getDockerMachineCmd() {
	    String command = "docker-machine";
	    String os = DockerUtil.getOS();
	    if (os != null && os.equalsIgnoreCase("osx")) {
	    		command = DockerUtil.DOCKER_MACHINE_MACOS;
	    }
	    return command;
	  }
	  
	  /**
	   * Parse String to Json data.
	   */
	  public static JsonNode jsonify(final String jsonString) throws DockerException {
	    try {
	      if (((!Objects.equal(jsonString, null)) || Objects.equal(jsonString, ""))) {
	        final ObjectMapper mapper = new ObjectMapper();
	        final JsonFactory factory = mapper.getJsonFactory();
	        final JsonParser parser = factory.createJsonParser(jsonString);
	        final JsonNode node = mapper.readTree(parser);
	        return node;
	      }
	      return null;
	    } catch (Throwable _e) {
	    		throw new DockerException(_e);
	    }
	  }
	  
	  /**
	   * Parse `docker-machine ls` host from the running environment.
	   */
	  public static Map<String, String> getHosts() throws DockerException {
	    Runtime runtime = Runtime.getRuntime();
	    final String data = DockerMachineHelper.listHostCmd(runtime);
	    Map<String, String> hosts = new HashMap<String, String>();
	    
	    if (data != null && !data.trim().isEmpty()) {
	    		String[] datas = data.split("\\r?\\n");
	    		int length = datas.length;
	    		// Remove columns
	    		String[] lines = Arrays.<String>copyOfRange(datas, 1, length);
	    		String l;
	    		for (final String line : lines) {
	    			System.out.println(line);
	    			l = line;
	    			// l = l.replaceAll("\\*", "");
	    			final String[] lsCmd = l.split("\\s+");
	    			if (((lsCmd.length >= 3) && (lsCmd.length < 5))) {
	    				hosts.put(lsCmd[0], lsCmd[2]);
	    			} else if (lsCmd.length >= 5) {
	    				hosts.put(lsCmd[0], lsCmd[3]);
	    			}
	    		}
	    }
	    return hosts;
	  }
	  
	  /**
	   * Get all existing hosts.
	   */
	  public static String getActiveHost() throws DockerException {
	    final Map<String, String> hosts = DockerUtil.getHosts();
	    Set<Map.Entry<String, String>> _entrySet = hosts.entrySet();
	    for (final Map.Entry<String, String> entry : _entrySet) {
	      String _value = entry.getValue();
	      boolean _equalsIgnoreCase = _value.equalsIgnoreCase(DockerUtil.HOST_RUNNING);
	      if (_equalsIgnoreCase) {
	        return entry.getKey();
	      }
	    }
	    Set<String> _keySet = hosts.keySet();
	    final String firstHost = ((String[])Conversions.unwrapArray(_keySet, String.class))[0];
	    InputOutput.<String>println(("first host" + firstHost));
	    Runtime _runtime = Runtime.getRuntime();
	    boolean _startCmd = DockerMachineHelper.startCmd(_runtime, firstHost);
	    if (_startCmd) {
	      return firstHost;
	    }
	    return null;
	  }
	  
	  /**
	   * Get all active hosts.
	   */
	  public static Map<String, String> getActiveHosts() throws DockerException {
	    Map<String, String> hosts = new HashMap<String, String>();
	    Map<String, String> _hosts = DockerUtil.getHosts();
	    Set<Map.Entry<String, String>> _entrySet = _hosts.entrySet();
	    for (final Map.Entry<String, String> entry : _entrySet) {
	      String _value = entry.getValue();
	      boolean _equalsIgnoreCase = _value.equalsIgnoreCase(DockerUtil.HOST_RUNNING);
	      if (_equalsIgnoreCase) {
	        String _key = entry.getKey();
	        String _value_1 = entry.getValue();
	        hosts.put(_key, _value_1);
	      }
	    }
	    return hosts;
	  }
	  
	  /**
	   * Get all active ones from hosts, without calling Docker again
	   */
	  public static Map<String, String> getActiveHosts(final Map<String, String> hosts) {
	    final Function2<String, String, Boolean> _function = (String host, String status) -> {
	      return Boolean.valueOf(DockerUtil.HOST_RUNNING.equalsIgnoreCase(status));
	    };
	    return MapExtensions.<String, String>filter(hosts, _function);
	  }
	  
	  /**
	   * Parse `docker-machine ls` command as table.
	   */
	  public static String getEnv(final String machineName) throws DockerException {
	    Runtime _runtime = Runtime.getRuntime();
	    final String data = DockerMachineHelper.getEnvCmd(_runtime, machineName);
	    List<String[]> hosts = new ArrayList<String[]>();
	    String[] result = null;
	    final String charset = "DOCKER_CERT_PATH";
	    boolean _notEquals = (!Objects.equal(data, null));
	    if (_notEquals) {
	      String[] st = data.split("\\r?\\n");
	      for (final String line : st) {
	        if ((line.startsWith("export") && line.contains(charset))) {
	          final String[] lsCmd = line.split("\\s+");
	          hosts.add(lsCmd);
	          String currentLine = lsCmd[1];
	          String[] _split = currentLine.split("=");
	          result = _split;
	          String _get = result[1];
	          return _get.replaceAll("\"", "");
	        }
	      }
	    }
	    String _property = System.getProperty("user.home");
	    final String defaultMachineCertPath = IterableExtensions.join(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_property, ".docker", "machine", "machines", machineName)), File.separator);
	    File _file = new File(defaultMachineCertPath);
	    boolean _canRead = _file.canRead();
	    if (_canRead) {
	      return defaultMachineCertPath;
	    }
	    return null;
	  }
	  
	  /**
	   * Delete a model.
	   */
	  public boolean deleteAllOldModels() {
	    boolean _xblockexpression = false;
	    {
	      final File myFile = new File("Models");
	      boolean _xifexpression = false;
	      boolean _isDirectory = myFile.isDirectory();
	      if (_isDirectory) {
	        _xifexpression = myFile.delete();
	      }
	      _xblockexpression = _xifexpression;
	    }
	    return _xblockexpression;
	  }
	  
	  /**
	   * Transform InputStream into String.
	   */
	  public static String asString(final InputStream response) {
	    final StringWriter logwriter = new StringWriter();
	    try {
	      final LineIterator itr = IOUtils.lineIterator(response, "UTF-8");
	      while (itr.hasNext()) {
	        {
	          String line = itr.next();
	          boolean _hasNext = itr.hasNext();
	          if (_hasNext) {
	            logwriter.write((line + "\n"));
	          } else {
	            logwriter.write((line + ""));
	          }
	        }
	      }
	      response.close();
	      return logwriter.toString();
	    } catch (final Throwable _t) {
	      if (_t instanceof IOException) {
	        final IOException e = (IOException)_t;
	        throw new RuntimeException(e);
	      } else {
	        throw Exceptions.sneakyThrow(_t);
	      }
	    } finally {
	      IOUtils.closeQuietly(response);
	    }
	  }
	  
	  /**
	   * Parse String in order to detect if it is Integer.
	   */
	  public static boolean isInteger(final String value) {
	    try {
	      Integer.parseInt(value);
	    } catch (final Throwable _t) {
	      if (_t instanceof NumberFormatException) {
	        final NumberFormatException e = (NumberFormatException)_t;
	        return false;
	      } else {
	        throw Exceptions.sneakyThrow(_t);
	      }
	    }
	    return true;
	  }
	  
	  /**
	   * Get the OS.
	   */
	  public static boolean isWindows() {
	    int _indexOf = DockerUtil.OS.indexOf("win");
	    return (_indexOf >= 0);
	  }
	  
	  public static boolean isMac() {
	    int _indexOf = DockerUtil.OS.indexOf("mac");
	    return (_indexOf >= 0);
	  }
	  
	  public static boolean isUnix() {
	    return (((DockerUtil.OS.indexOf("nix") >= 0) || (DockerUtil.OS.indexOf("nux") >= 0)) || (DockerUtil.OS.indexOf("aix") > 0));
	  }
	  
	  public static boolean isSolaris() {
	    int _indexOf = DockerUtil.OS.indexOf("sunos");
	    return (_indexOf >= 0);
	  }
	  
	  public static String getOS() {
	    boolean _isWindows = DockerUtil.isWindows();
	    if (_isWindows) {
	      return "win";
	    } else {
	      boolean _isMac = DockerUtil.isMac();
	      if (_isMac) {
	        return "osx";
	      } else {
	        boolean _isUnix = DockerUtil.isUnix();
	        if (_isUnix) {
	          return "uni";
	        } else {
	          boolean _isSolaris = DockerUtil.isSolaris();
	          if (_isSolaris) {
	            return "sol";
	          } else {
	            return "err";
	          }
	        }
	      }
	    }
	  }
	}



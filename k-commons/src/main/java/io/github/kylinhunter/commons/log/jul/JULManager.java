/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.log.jul;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.reflect.ClassUtil;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.LogManager;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 16:41
 */
public class JULManager {

  private static volatile Boolean initialized;

  private static final String DEFAULT_CONFIG_FILE = "k-jul-logging.properties";

  /**
   * @return boolean
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-10-17 23:45
   */
  public static boolean init() {
    return init(DEFAULT_CONFIG_FILE, true);
  }

  public static boolean init(boolean slf4jSupported) {
    return init(DEFAULT_CONFIG_FILE, slf4jSupported);
  }

  public static boolean init(String configFile) {
    return init(configFile, true);
  }

  /**
   * @return boolean
   * @title init
   * @description
   * @author BiJi'an
   * @date 2023-04-22 16:41
   */
  public static synchronized boolean init(String configFile, boolean slf4jSupported) {
    if (initialized != null) {
      return initialized;
    }
    synchronized (JULManager.class) {
      if (initialized != null) {
        return initialized;
      }
      try {
        if (StringUtil.isEmpty(configFile)) {
          configFile = DEFAULT_CONFIG_FILE;
        }
        readConfiguration(configFile);

        if (slf4jSupported) {
          trySlf4jSupport(configFile);
        }
        initialized = true;
      } catch (Exception e) {
        System.err.println("init error:" + e.getMessage());
        initialized = false;
      }
      return initialized;
    }
  }

  /**
   * @param configFile configFile
   * @title initFromConfiguration
   * @description initFromConfiguration
   * @author BiJi'an
   * @date 2023-10-17 23:57
   */
  private static void readConfiguration(String configFile) {

    try (InputStream inputStream = ResourceHelper.getInputStreamInClassPath(configFile)) {
      LogManager logManager = LogManager.getLogManager();
      logManager.readConfiguration(inputStream);
    } catch (Exception e) {
      throw new GeneralException("read " + configFile + "error:" + e.getMessage());
    }
  }

  private static Properties readProperties(String configFile) {

    try (InputStream inputStream = ResourceHelper.getInputStreamInClassPath(configFile)) {
      Properties properties = new Properties();
      properties.load(inputStream);
      return properties;
    } catch (Exception e) {
      System.err.println("read readProperties error" + e.getMessage());
    }
    return null;
  }

  /**
   * @param configFile configFile
   * @title trySlf4jSupport
   * @description
   *     <p>duplicate log outputs
   *     <p>Jul bridging SLF4J failure for duplicate log output
   *     <p>Solution1: configure in jul configuration file,just like:
   *     <p>handlers =org.slf4j.bridge.SLF4JBridgeHandler
   *     <p>Solution2: remove handlers for root logger ,and then install
   *     <p>SLF4JBridgeHandler.removeHandlersForRootLogger();
   *     <p>SLF4JBridgeHandler.install();
   * @author BiJi'an
   * @date 2023-10-18 01:04
   */
  public static void trySlf4jSupport(String configFile) {

    Properties properties = readProperties(configFile);
    Objects.requireNonNull(properties);
    String handlers = properties.getProperty("handlers");
    if (StringUtil.isEmpty(handlers) || !handlers.contains("SLF4J")) {
      trySlf4jSupport();
    }
  }

  /**
   * @title trySlf4jSupport
   * @description trySlf4jSupport
   *     <p>SLF4JBridgeHandler.removeHandlersForRootLogger();
   *     <p>SLF4JBridgeHandler.install();
   * @author BiJi'an
   * @date 2023-10-18 01:05
   */
  public static void trySlf4jSupport() {
    Class<Object> clazz = ClassUtil.findClass("org.slf4j.bridge.SLF4JBridgeHandler");
    if (clazz != null) {
      Method methodRemoveHandlersForRootLogger =
          ReflectUtils.getMethod(clazz, "removeHandlersForRootLogger");
      ReflectUtils.invoke(null, methodRemoveHandlersForRootLogger);

      Method methodInstall = ReflectUtils.getMethod(clazz, "install");
      ReflectUtils.invoke(null, methodInstall);
    }
  }

  /**
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-10-20 01:48
   */
  public static void reset() {
    initialized = null;
  }
}

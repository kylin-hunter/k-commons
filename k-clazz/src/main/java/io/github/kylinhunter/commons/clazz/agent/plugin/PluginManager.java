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
package io.github.kylinhunter.commons.clazz.agent.plugin;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.log.jul.JULManager;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:50
 */
public class PluginManager {
  private static Logger log;

  private static final List<Plugin> plugins = new ArrayList<>();

  /**
   * @return void
   * @title initLog
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:47
   */
  private static void initLog() {
    JULManager.init();
    log = Logger.getLogger(PluginManager.class.toString());
  }

  /**
   * @return void
   * @title loadPlugins
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:47
   */
  private static void loadPlugins(Instrumentation inst) {

    ServiceLoader<Plugin> allPlugins = ServiceLoader.load(Plugin.class);
    log.info("load plugins  start");
    allPlugins.forEach(
        plugin -> {
          log.info("load plugins: " + plugin.getName());
          plugin.init(inst);
          plugins.add(plugin);
        });
    log.info("load plugins  end");
  }

  /**
   * @param agentArgs agentArgs
   * @param inst inst
   * @return void
   * @title initialize
   * @description
   * @author BiJi'an
   * @date 2023-03-19 00:19
   */
  public static void initialize(String agentArgs, Instrumentation inst) {
    initLog();
    log.info("initialize ,agentArgs=" + agentArgs);
    AgentArgsHelper.init(agentArgs);
    loadPlugins(inst);
  }
}

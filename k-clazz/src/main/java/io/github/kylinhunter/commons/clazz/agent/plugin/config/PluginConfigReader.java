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
package io.github.kylinhunter.commons.clazz.agent.plugin.config;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;
import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import java.util.Properties;
import o.github.kylinhunter.commons.utils.properties.PropertiesHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-15 22:40
 */
public class PluginConfigReader {
  private static final String FIX_PREFIX = "plugins.";

  /**
   * @param clazz clazz
   * @param pluginName pluginName
   * @return T
   * @title buildConfig
   * @description
   * @author BiJi'an
   * @date 2023-04-15 22:44
   */
  public <T extends PluginConfig> T read(Class<T> clazz, String pluginName) {
    Properties properties = loadProperties(pluginName);
    return PropertiesHelper.toBean(properties, clazz);
  }

  /**
   * @return java.util.Properties
   * @title loadProperties
   * @description
   * @author BiJi'an
   * @date 2023-04-15 22:06
   */
  protected Properties loadProperties(String pluginName) {

    String configFile = AgentArgsHelper.getConfigFilePath();
    ExceptionChecker.checkNotEmpty(configFile, " no config file be specified ");
    Properties propertiesOld = PropertiesHelper.load(configFile);
    Properties propertiesNew = new Properties();
    String prefix = FIX_PREFIX + pluginName + ".";
    propertiesOld.forEach(
        (k, v) -> {
          String key = (String) k;
          if (key.startsWith(prefix)) {
            propertiesNew.put(key.substring(prefix.length()), v);
          }
        });
    return propertiesNew;
  }
}

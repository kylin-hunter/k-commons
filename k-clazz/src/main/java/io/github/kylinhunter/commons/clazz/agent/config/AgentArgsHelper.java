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
package io.github.kylinhunter.commons.clazz.agent.config;

import io.github.kylinhunter.commons.collections.MultiValueMap;
import io.github.kylinhunter.commons.lang.strings.StringUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:30
 */
public class AgentArgsHelper {
  private static final MultiValueMap<String, String> AGENT_ARGS = new MultiValueMap<>();
  private static final String CONFIG_FILE = "config-file";

  /**
   * @param agentArgs agentArgs
   * @title init
   * @description
   * @author BiJi'an
   * @date 2023-03-19 14:37
   */
  public static void init(String agentArgs) {

    String[] propGroups = StringUtil.split(agentArgs, ';');
    if (propGroups != null) {
      for (String propGroup : propGroups) {
        String[] propPairs = StringUtil.split(propGroup, '=');
        if (propPairs != null && propPairs.length == 2) {
          AGENT_ARGS.add(propPairs[0], propPairs[1]);
        }
      }
    }
  }

  /**
   * @return void
   * @title ConfigFile
   * @description
   * @author BiJi'an
   * @date 2023-04-14 14:13
   */
  public static String getConfigFilePath() {
    return AGENT_ARGS.getValue(CONFIG_FILE);
  }
}

package io.github.kylinhunter.commons.clazz.agent.config;

import io.github.kylinhunter.commons.collections.MultiValueMap;
import org.apache.commons.lang3.StringUtils;

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
   * @return void
   * @title init
   * @description
   * @author BiJi'an
   * @date 2023-03-19 14:37
   */
  public static void init(String agentArgs) {

    String[] propGroups = StringUtils.split(agentArgs, ';');
    if (propGroups != null && propGroups.length > 0) {
      for (String propGroup : propGroups) {
        String[] propPairs = StringUtils.split(propGroup, '=');
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

package io.github.kylinhunter.commons.clazz.agent.config;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.properties.PropertiesHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:30
 **/
public class AgentConfigReader {
    private static final AgentConfig agentConfig = new AgentConfig();

    public static void init(String agentArgs) {
        String[] propGroups = StringUtils.split(agentArgs, ';');
        if (propGroups != null && propGroups.length > 0) {
            for (String propGroup : propGroups) {
                String[] propPairs = StringUtils.split(propGroup, '=');
                if (propPairs != null && propPairs.length == 2) {
                    agentConfig.add(propPairs[0], propPairs[1]);
                }
            }
        }

    }

    public static <T> T loadConfig(Class<T> clazz) {

        String configFile = agentConfig.getValue("config-file");
        if (!StringUtils.isEmpty(configFile)) {
            return PropertiesHelper.load(configFile, clazz);
        }
        return null;

    }
}

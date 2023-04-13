package io.github.kylinhunter.commons.clazz.agent.config;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.clazz.agent.plugin.AgentTransformer;
import io.github.kylinhunter.commons.clazz.agent.plugin.Plugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfig;
import io.github.kylinhunter.commons.clazz.exception.AgentException;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.properties.PropertiesHelper;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:30
 **/
public class AgentArgsHelper {
    private static final AgentArgs AGENT_ARGS = new AgentArgs();
    private static final Map<Class<?>, Object> configs = MapUtils.newHashMap();
    private static final String FIX_PREFIX = "plugins.";

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
     * @param plugin plugin
     * @return T
     * @title loadConfig
     * @description
     * @author BiJi'an
     * @date 2023-03-19 14:43
     */
    @SuppressWarnings("unchecked")
    public static <T extends PluginConfig, V extends AgentTransformer> T loadConfig(Plugin<T, V> plugin) {
        Class<T> configDefinition = GenericTypeUtils.getSuperClassActualType(plugin.getClass(), 0);
        T t = (T) configs.get(configDefinition);
        if (t != null) {
            return t;
        } else {
            String configFile = AGENT_ARGS.getConfigFile();
            if (StringUtils.isEmpty(configFile)) {
                throw new AgentException(" no config file be specified ");
            } else {
                String name = plugin.getName();
                Properties properties = PropertiesHelper.load(configFile);
                Properties propertiesNew = new Properties();
                String prefix = FIX_PREFIX + name + ".";
                properties.forEach((k, v) -> {
                    String key = (String) k;
                    if (key.startsWith(prefix)) {
                        propertiesNew.put(key.substring(prefix.length()), v);
                    }
                });
                return PropertiesHelper.toBean(propertiesNew, configDefinition);
            }
        }

    }
}

package io.github.kylinhunter.commons.clazz.agent.plugin.config;

import java.util.Properties;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;
import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.properties.PropertiesHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-15 22:40
 **/
public class PluginConfigReader {
    private static final String FIX_PREFIX = "plugins.";

    /**
     * @param clazz      clazz
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
        propertiesOld.forEach((k, v) -> {
            String key = (String) k;
            if (key.startsWith(prefix)) {
                propertiesNew.put(key.substring(prefix.length()), v);
            }
        });
        return propertiesNew;
    }
}

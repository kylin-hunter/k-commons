package io.github.kylinhunter.commons.clazz.agent.config;

import java.io.File;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfig;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.properties.PropertiesHelper;

class AgentArgsHelperTest {

    @Test
    void getConfig() {

        File file = ResourceHelper.getFileInClassPath("k-agent-plugin-config.properties");

        Properties properties = PropertiesHelper.load(file.getAbsolutePath());
        properties.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });

        System.out.println(file.getAbsolutePath());
        AgentArgsHelper.init("config-file=" + file.getAbsolutePath());

        PluginConfig config = AgentArgsHelper.getConfig(PluginConfig.class);

    }
}
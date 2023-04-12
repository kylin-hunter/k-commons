package io.github.kylinhunter.commons.clazz.agent.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import io.github.kylinhunter.commons.clazz.agent.plugin.Plugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfig;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.properties.PropertiesHelper;

class AgentArgsHelperTest {

    @Mock
    Plugin plugin;

    @Test
    void getConfig() {
        plugin =mock(Plugin.class);
        when(plugin.getName()).thenReturn("test");
        System.out.println(plugin.getName());
        System.out.println(plugin.getName());
        File file = ResourceHelper.getFileInClassPath("k-agent-plugin-config.properties");

        Properties properties = PropertiesHelper.load(file.getAbsolutePath());
        properties.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });

        System.out.println(file.getAbsolutePath());
        AgentArgsHelper.init("config-file=" + file.getAbsolutePath());

        PluginConfig pluginConfig = AgentArgsHelper.getConfig(PluginConfig.class, plugin);
        System.out.println(pluginConfig);

    }
}
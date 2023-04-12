package io.github.kylinhunter.commons.agent.invoke;

import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.Plugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfig;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.properties.PropertiesHelper;

class InvokeAnalysisConfigTest {

    @Test
    void getConfig() {
        Plugin plugin = new InvokeAnalysisPlugin();
        System.out.println(plugin.getName());
        System.out.println(plugin.getName());
        File file = ResourceHelper.getFileInClassPath("k-agent-plugin-invoke-analysis.properties");
        AgentArgsHelper.init("config-file=" + file.getAbsolutePath());

        InvokeAnalysisConfig pluginConfig = AgentArgsHelper.getConfig(InvokeAnalysisConfig.class, plugin);
        System.out.println(pluginConfig);

    }
}
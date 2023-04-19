package io.github.kylinhunter.commons.clazz.agent.plugin.config;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;
import io.github.kylinhunter.commons.io.ResourceHelper;

class PluginConfigReaderTest {

    @Test
    void read() {
        MockedStatic<AgentArgsHelper> agentArgsHelper = Mockito.mockStatic(AgentArgsHelper.class);
        File file = ResourceHelper.getFileInClassPath("k-agent-plugin-config.properties");
        System.out.println(file.getAbsolutePath());
        agentArgsHelper.when(() -> AgentArgsHelper.getConfigFilePath()).thenReturn(file.getAbsolutePath());
        PluginConfigReader pluginConfigReader = new PluginConfigReader();
        PluginConfig pluginConfig = pluginConfigReader.read(PluginConfig.class, "test");
        System.out.println(pluginConfig);

        Assertions.assertEquals("12", pluginConfig.getPoints().get(0).getType().getNameContains());
        Assertions.assertEquals("42", pluginConfig.getPoints().get(1).getMethod().getNameContains());
        agentArgsHelper.close();
    }
}
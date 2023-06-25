package io.github.kylinhunter.commons.clazz.agent.plugin.config;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;
import io.github.kylinhunter.commons.clazz.exception.AgentException;
import io.github.kylinhunter.commons.io.ResourceHelper;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class PluginConfigReaderTest {

  @Test
  void read() {
    MockedStatic<AgentArgsHelper> agentArgsHelper = Mockito.mockStatic(AgentArgsHelper.class);
    File file1 = ResourceHelper.getFileInClassPath("k-agent-plugin-test.properties");
    File file2 = ResourceHelper.getFileInClassPath("k-agent-plugin-test2.properties");

    agentArgsHelper
        .when(() -> AgentArgsHelper.getConfigFilePath())
        .thenReturn(file1.getAbsolutePath(), file2.getAbsolutePath());

    PluginConfigReader pluginConfigReader = new PluginConfigReader();
    PluginConfig pluginConfig = pluginConfigReader.read(PluginConfig.class, "test");
    System.out.println(pluginConfig);

    Assertions.assertEquals("12", pluginConfig.getPoints().get(0).getType().getNameContains());
    Assertions.assertEquals("42", pluginConfig.getPoints().get(1).getMethod().getNameContains());

    Assertions.assertThrows(AgentException.class, () -> {
      pluginConfigReader.read(PluginConfig.class, "test2");
    });
    agentArgsHelper.close();

  }
}

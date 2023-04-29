package io.github.kylinhunter.commons.agent.invoke;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfigReader;
import io.github.kylinhunter.commons.io.ResourceHelper;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class InvokeAnalysisConfigTest {

  @Test
  void getConfig() {
    MockedStatic<AgentArgsHelper> agentArgsHelper = Mockito.mockStatic(AgentArgsHelper.class);
    File file = ResourceHelper.getFileInClassPath("k-agent-plugin-invoke-analysis.properties");
    System.out.println(file.getAbsolutePath());
    agentArgsHelper
        .when(() -> AgentArgsHelper.getConfigFilePath())
        .thenReturn(file.getAbsolutePath());
    PluginConfigReader pluginConfigReader = new PluginConfigReader();
    InvokeAnalysisConfig pluginConfig =
        pluginConfigReader.read(InvokeAnalysisConfig.class, "invoke-analysis");
    System.out.println(pluginConfig);

    Assertions.assertEquals(
        "io.github.kylinhunter.commons.agent.invoke.test",
        pluginConfig.getPoints().get(0).getType().getNameStartsWith());
    Assertions.assertEquals(
        "doHomeWork", pluginConfig.getPoints().get(0).getMethod().getNameContains());
    agentArgsHelper.close();
  }
}

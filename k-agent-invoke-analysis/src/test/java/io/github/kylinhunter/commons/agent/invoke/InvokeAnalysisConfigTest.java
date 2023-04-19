package io.github.kylinhunter.commons.agent.invoke;

import java.io.File;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.Plugin;
import io.github.kylinhunter.commons.io.ResourceHelper;

class InvokeAnalysisConfigTest {

    @Test
    void getConfig() {
        InvokeAnalysisPlugin plugin = new InvokeAnalysisPlugin();
        System.out.println(plugin.getName());
        System.out.println(plugin.getName());
        File file = ResourceHelper.getFileInClassPath("k-agent-plugin-invoke-analysis.properties");
        AgentArgsHelper.init("config-file=" + file.getAbsolutePath());

//        InvokeAnalysisConfig pluginConfig = AgentArgsHelper.loadConfig(plugin);
//        System.out.println(pluginConfig);

    }
}
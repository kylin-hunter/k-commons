package io.github.kylinhunter.commons.clazz.agent.config;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.properties.PropertiesHelper;

class AgentArgsHelperTest {

    @Test
    void getConfig() {
        AgentArgsHelper
                .init("config-file=/Users/bijian/workspace_gitee/k-commons/k-clazz-tools/src/main/resources/k-agent"
                        + "-plugin-invoke-analysis.properties");
        //        AgentArgsHelper.getConfig()
        Properties properties =
                PropertiesHelper.load("/Users/bijian/workspace_gitee/k-commons/k-clazz-tools/src/main/resources/k-agent"
                        + "-plugin-invoke-analysis.properties");
        properties.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
}
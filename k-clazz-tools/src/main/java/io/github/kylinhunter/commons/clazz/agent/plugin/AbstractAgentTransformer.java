package io.github.kylinhunter.commons.clazz.agent.plugin;

import io.github.kylinhunter.commons.clazz.agent.plugin.bean.PluginPoint;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;
import lombok.Data;

@Data
public abstract class AbstractAgentTransformer implements AgentTransformer {
    protected PluginPoint pluginPoint;
    protected PluginConfig pluginConfig;

    @Override
    public void init(PluginConfig pluginConfig, PluginPoint pluginPoint) {
        this.pluginConfig = pluginConfig;
        this.pluginPoint = pluginPoint;
    }

}

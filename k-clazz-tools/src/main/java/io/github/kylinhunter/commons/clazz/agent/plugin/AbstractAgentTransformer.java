package io.github.kylinhunter.commons.clazz.agent.plugin;

import lombok.Data;

@Data
public abstract class AbstractAgentTransformer implements AgentTransformer {

    protected PluginPoint pluginPoint;

}

package io.github.kylinhunter.commons.clazz.agent.plugin;

import net.bytebuddy.agent.builder.AgentBuilder;

public interface AgentTransformer extends AgentBuilder.Transformer {

    void setPluginPoint(PluginPoint pluginPoint);

}

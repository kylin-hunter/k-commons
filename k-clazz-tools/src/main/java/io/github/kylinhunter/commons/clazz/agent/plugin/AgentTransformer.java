package io.github.kylinhunter.commons.clazz.agent.plugin;

import io.github.kylinhunter.commons.clazz.agent.plugin.bean.PluginPoint;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;
import net.bytebuddy.agent.builder.AgentBuilder;

public interface AgentTransformer extends AgentBuilder.Transformer {

  void init(PluginConfig pluginConfig, PluginPoint pluginPoint);
}

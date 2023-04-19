package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.lang.instrument.Instrumentation;

import io.github.kylinhunter.commons.clazz.agent.plugin.config.bean.PluginConfig;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:46
 **/
public interface Plugin<T extends PluginConfig> {
    String getName();

    T getConfig();

    void init(Instrumentation inst);

    Class<? extends AgentTransformer> transformerDefinition();
}

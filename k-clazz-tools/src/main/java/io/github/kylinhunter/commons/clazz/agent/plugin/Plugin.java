package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.util.List;

import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfig;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:46
 **/
public interface Plugin<T extends PluginConfig, V extends AgentTransformer> {
    String getName();

    List<PluginPoint> getPluginPoints();

    void setConfig(T t);

    void init();

    T getConfig();

    Class adviceClass();

    Class<V> getTransformer();

    void setTransformer( Class<V> v);

}

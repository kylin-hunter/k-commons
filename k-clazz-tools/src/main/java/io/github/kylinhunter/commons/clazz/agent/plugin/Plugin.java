package io.github.kylinhunter.commons.clazz.agent.plugin;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:46
 **/
public interface Plugin {
    String name();

    PluginPoint[] buildPluginPoint();

    Class adviceClass();

    void other();

}

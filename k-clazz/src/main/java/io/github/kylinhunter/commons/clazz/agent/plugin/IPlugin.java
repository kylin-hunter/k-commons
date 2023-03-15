package io.github.kylinhunter.commons.clazz.agent.plugin;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:46
 **/
public interface IPlugin {
    String name();

    InterceptPoint[] buildInterceptPoint();

    Class adviceClass();
    void other();

}

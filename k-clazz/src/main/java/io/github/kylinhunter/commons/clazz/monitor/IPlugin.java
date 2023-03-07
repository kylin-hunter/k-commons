package io.github.kylinhunter.commons.clazz.monitor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:46
 **/
public interface IPlugin {
    //名称
    String name();

    //监控点
    InterceptPoint[] buildInterceptPoint();

    //拦截器类
    Class adviceClass();

}

package io.github.kylinhunter.commons.clazz.agent.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:47
 **/
public interface InterceptPoint {
    //类匹配规则
    ElementMatcher<TypeDescription> buildTypesMatcher();

    //方法匹配规则
    ElementMatcher<MethodDescription> buildMethodsMatcher();
}

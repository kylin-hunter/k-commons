package io.github.kylinhunter.commons.clazz.monitor;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:48
 **/
public class JvmPlugin implements IPlugin {

    @Override
    public String name() {
        return "jvm";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {

                        return ElementMatchers.nameStartsWith("io.github.kylinhunter.commons.clazz.test");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")));
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return JvmAdvice.class;
    }

}

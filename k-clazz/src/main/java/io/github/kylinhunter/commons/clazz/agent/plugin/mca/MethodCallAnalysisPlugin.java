package io.github.kylinhunter.commons.clazz.agent.plugin.mca;

import io.github.kylinhunter.commons.clazz.agent.plugin.IPlugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.InterceptPoint;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:48
 **/
public class MethodCallAnalysisPlugin implements IPlugin {

    @Override
    public String name() {
        return "timecost";
    }

    @Override
    public InterceptPoint[] buildInterceptPoint() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {

                        return ElementMatchers.nameStartsWith("io.github.kylinhunter.commons.clazz.agent.plugin.mca.test");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod().and(ElementMatchers.any());
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return MethodCostTime.class;
    }

    @Override
    public void other() {

    }

}

package io.github.kylinhunter.commons.clazz.agent.plugin.invoke;

import io.github.kylinhunter.commons.clazz.agent.plugin.Plugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.PluginConfig;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 *  -javaagent:/Users/bijian/workspace_gitee/k-commons/k-clazz/build/libs/k-clazz-1.0.1.jar=config-file=/Users/bijian/workspace_gitee/k-commons/k-clazz/src/main/resources/k-invoke-analysis.properties
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:48
 **/
public class InvokeAnalysisPlugin implements Plugin {

    @Override
    public String name() {
        return "timecost";
    }

    @Override
    public PluginConfig[] buildInterceptPoint() {
        return new PluginConfig[]{
                new PluginConfig() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {

                        return ElementMatchers.nameStartsWith("io.github.kylinhunter.commons.clazz.agent.plugin.invoke"
                                + ".test");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod().and(ElementMatchers.nameContains("doHomeWork"));
                    }
                }
        };
    }

    @Override
    public Class adviceClass() {
        return InvokeAnalysis.class;
    }

    @Override
    public void other() {

    }

}

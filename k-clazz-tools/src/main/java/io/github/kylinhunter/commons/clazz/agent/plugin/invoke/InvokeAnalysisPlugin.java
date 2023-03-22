package io.github.kylinhunter.commons.clazz.agent.plugin.invoke;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import io.github.kylinhunter.commons.clazz.agent.plugin.Plugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.PluginConfig1;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * -javaagent:/Users/bijian/workspace_gitee/k-commons/k-clazz-tools/build/libs/k-clazz-tools-1.0.1
 * .jar=config-file=/Users/bijian/workspace_gitee/k-commons/k-clazz-tools/src/main/resources/k-invoke-analysis
 * .properties
 *
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
    public PluginConfig1[] buildInterceptPoint() {

        InvokeAnalysisConfig config = AgentArgsHelper.getConfig(InvokeAnalysisConfig.class);
        return new PluginConfig1[] {
                new PluginConfig1() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {

                        return ElementMatchers
                                .nameStartsWith("io.github.kylinhunter.commons.clazz.agent.plugin.invoke" + ".test");
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

package io.github.kylinhunter.commons.clazz.agent;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

import io.github.kylinhunter.commons.clazz.agent.plugin.IPlugin;
import io.github.kylinhunter.commons.clazz.agent.plugin.InterceptPoint;
import io.github.kylinhunter.commons.clazz.agent.plugin.PluginFactory;
import io.github.kylinhunter.commons.clazz.agent.plugin.mca.MethodCostTime;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.util.OnceRunner;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-29 00:23
 **/
public class PreMain {
    /**
     * @param agentArgs agentArgs
     * @param inst      inst
     * @return void
     * @title java -javaagent:agent.jar=agentArgs
     * @description
     * @author BiJi'an
     * @date 2023-03-07 23:09
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        OnceRunner.run(PreMain.class, () -> {
            System.out.println("premain start,agentArgs=" + agentArgs);

            AgentListenr agentListenr = new AgentListenr();

            AgentBuilder.Identified.Extendable agentBuilder = null;
            for (IPlugin iPlugin : PluginFactory.pluginGroup) {
                final InterceptPoint[] interceptPoints = iPlugin.buildInterceptPoint();
                if (interceptPoints != null) {
                    for (InterceptPoint interceptPoint : interceptPoints) {
                        if (interceptPoint != null) {
                            AgentBuilder.Default builder = new AgentBuilder.Default();
                            final ElementMatcher<TypeDescription> typeDescriptionElementMatcher =
                                    interceptPoint.buildTypesMatcher();
                            if (typeDescriptionElementMatcher != null) {
                                builder.type(typeDescriptionElementMatcher)
                                        .transform(
                                                (builder1, typeDescription, classLoader, module,
                                                 protectionDomain) -> {
                                                    DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<?>
                                                            intercept = null;
                                                    try {


                                                        builder1= builder1.method(interceptPoint.buildMethodsMatcher())
                                                                        .intercept(MethodDelegation
                                                                                .to(MethodCostTime.class));
                                                        builder1
                                                                .make().saveIn(UserDirUtils.getTmpDir("bja"));

                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        throw new RuntimeException("1");
                                                    }
                                                    return builder1;

                                                }).with(agentListenr).installOn(inst);
                            }
                        }

                    }
                }

                iPlugin.other();
            }

        });

    }
}

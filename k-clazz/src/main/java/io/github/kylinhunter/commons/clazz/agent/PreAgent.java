package io.github.kylinhunter.commons.clazz.agent;

import static net.bytebuddy.matcher.ElementMatchers.named;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.ResettableClassFileTransformer;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-29 00:23
 **/
public class PreAgent {

    /**
     * @param agentArgs agentArgs
     * @param inst      inst
     * @return void
     * @title java -javaagent:agent.jar=agentArgs
     * @description
     * @author BiJi'an
     * @date 2022-12-29 00:23
     */

    private static boolean a = false;

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        if (a == true) {

            System.out.println("return");
            return;
        }
        a = true;
        System.out.println("112基于javaagent链路追踪｛源码微信公众号：bugstack虫洞栈｝");
        System.out.println("==========================================================\r\n");

        AgentBuilder.Transformer transformer1 = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription,
                                                    ClassLoader classLoader, JavaModule module,
                                                    ProtectionDomain protectionDomain) {
                return builder.method(named("aaa")).intercept(FixedValue.value("bijian"));
            }
        };

        AgentBuilder.Transformer transformer2 = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription,
                                                    ClassLoader classLoader, JavaModule module,
                                                    ProtectionDomain protectionDomain) {
                return builder.method(named("aaa")).intercept(MethodDelegation.to(Target.class));
            }
        };

        //监听
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader,
                                         JavaModule javaModule, boolean b, DynamicType dynamicType) {
                System.out.println("1onTransformation：" + typeDescription);
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule,
                                  boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b,
                                Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        AgentBuilder.Default builder = new AgentBuilder.Default();

        AgentBuilder.Identified.Extendable transform = builder
                .type(ElementMatchers.nameStartsWith("io.github.kylinhunter.commons.clazz.test"))

                .transform(transformer1);

        final ResettableClassFileTransformer resettableClassFileTransformer = transform.with(listener).installOn(inst);

        transform = builder
                .type(ElementMatchers.nameStartsWith("io.github.kylinhunter.commons.clazz.test"))
                .transform(transformer2);

        transform.with(listener).installOn(inst);

    }
}

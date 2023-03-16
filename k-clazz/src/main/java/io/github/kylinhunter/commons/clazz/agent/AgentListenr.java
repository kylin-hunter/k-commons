package io.github.kylinhunter.commons.clazz.agent;


import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;
import net.bytebuddy.utility.nullability.MaybeNull;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 23:12
 **/
public class AgentListenr implements AgentBuilder.Listener {

    @Override
    public void onDiscovery(String typeName, @MaybeNull ClassLoader classLoader, @MaybeNull JavaModule module,
                            boolean loaded) {
        //        System.out.println("onDiscovery：" + typeName);
    }

    @Override

    public void onTransformation(TypeDescription typeDescription, @MaybeNull ClassLoader classLoader,
                                 @MaybeNull JavaModule module, boolean loaded, DynamicType dynamicType) {
        System.out.println("onTransformation：" + typeDescription);
    }

    @Override
    public void onIgnored(TypeDescription typeDescription, @MaybeNull ClassLoader classLoader,
                          @MaybeNull JavaModule module, boolean loaded) {
        //        System.out.println("onIgnored：" + typeDescription);
    }

    @Override
    public void onError(String typeName, @MaybeNull ClassLoader classLoader, @MaybeNull JavaModule module,
                        boolean loaded, Throwable throwable) {
        System.out.println("onError：" + typeName);
        throwable.printStackTrace();
    }

    @Override
    public void onComplete(String typeName, @MaybeNull ClassLoader classLoader, @MaybeNull JavaModule module,
                           boolean loaded) {
        //        System.out.println("onComplete：" + typeName);
    }
}

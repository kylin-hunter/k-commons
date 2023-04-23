package io.github.kylinhunter.commons.clazz.agent;

import java.util.logging.Logger;

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
@SuppressWarnings("NullableProblems")
public class AgentListenr implements AgentBuilder.Listener {
    public static Logger log = Logger.getLogger(AgentListenr.class.toString());

    @Override
    public void onDiscovery(String typeName, @MaybeNull ClassLoader classLoader, @MaybeNull JavaModule module,
                            boolean loaded) {
        //log.info("onDiscovery：" + typeName);
    }

    @Override
    public void onTransformation(TypeDescription typeDescription, @MaybeNull ClassLoader classLoader,
                                 @MaybeNull JavaModule module, boolean loaded, DynamicType dynamicType) {
        log.info("onTransformation：" + typeDescription);
    }

    @Override
    public void onIgnored(TypeDescription typeDescription, @MaybeNull ClassLoader classLoader,
                          @MaybeNull JavaModule module, boolean loaded) {
        //log.info("onIgnored：" + typeDescription);
    }

    @Override
    public void onError(String typeName, @MaybeNull ClassLoader classLoader, @MaybeNull JavaModule module,
                        boolean loaded, Throwable throwable) {
        log.info("onError：" + typeName + "=>" + throwable.getMessage());
        throwable.printStackTrace();
    }

    @Override
    public void onComplete(String typeName, @MaybeNull ClassLoader classLoader, @MaybeNull JavaModule module,
                           boolean loaded) {
        //log.info("onComplete：" + typeName);
    }
}

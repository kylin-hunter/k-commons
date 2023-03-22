package io.github.kylinhunter.commons.clazz.agent.plugin.invoke;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import io.github.kylinhunter.commons.clazz.agent.config.AgentArgsHelper;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 10:36
 **/
public class InvokeAnalysis {

    private static InvokeTraceManager invokeTraceManager = InvokeTraceManager.getInstance();

    private static AgentArgsHelper agentArgsHelper =new AgentArgsHelper();
    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] arguments,
                                   @SuperCall Callable<?> callable)
            throws Exception {
        Thread thread = Thread.currentThread();
        InvokeTrace invokeTrace = new InvokeTrace(thread.getId(), thread.getStackTrace(), method);

        try {
            return callable.call();
        } finally {

            invokeTrace.end();
            invokeTraceManager.addTrace(invokeTrace);

        }
    }
}

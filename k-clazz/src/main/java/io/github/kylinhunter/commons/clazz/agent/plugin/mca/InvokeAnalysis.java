package io.github.kylinhunter.commons.clazz.agent.plugin.mca;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 10:36
 **/
public class InvokeAnalysis {

    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] a, @SuperMethod Method b,
                                   @Super Object ooo,
                                   @SuperCall Callable<?> callable) throws Exception {

        InvokeTrace invokeTrace = new InvokeTrace(Thread.currentThread().getStackTrace(), method);
        try {
            return callable.call();
        } finally {

            invokeTrace.end();

        }
    }
}

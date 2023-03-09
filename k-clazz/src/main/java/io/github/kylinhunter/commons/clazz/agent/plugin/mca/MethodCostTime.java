package io.github.kylinhunter.commons.clazz.agent.plugin.mca;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-08 10:36
 **/
public class MethodCostTime {


    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @AllArguments Object[]a, @SuperMethod Method b,

                                   @SuperCall Callable<?> callable ,@This Object thisObj) throws Exception {
        long start = System.currentTimeMillis();
        try {
            System.out.println("from "+thisObj.getClass());

            final Object call = callable.call();
            return call;
        } finally {
            System.out.println(method + "" + (System.currentTimeMillis() - start) + "ms");
        }
    }
}

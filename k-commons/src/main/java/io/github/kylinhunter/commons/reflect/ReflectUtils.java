package io.github.kylinhunter.commons.reflect;

import java.lang.reflect.Method;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-15 16:37
 **/
public class ReflectUtils {

    /**
     * @param method method
     * @param obj    obj
     * @param args   args
     * @return java.lang.Object
     * @title invoke
     * @description
     * @author BiJi'an
     * @date 2023-02-10 10:38
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object obj, Method method, Object... args) {
        try {
            return (T) method.invoke(obj, args);
        } catch (Exception e) {
            throw new InitException("invoke error", e);
        }
    }
}

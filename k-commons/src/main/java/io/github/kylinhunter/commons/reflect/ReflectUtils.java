package io.github.kylinhunter.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 16:37
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
            method.setAccessible(true);
            return (T) method.invoke(obj, args);
        } catch (Exception e) {
            throw new InitException("invoke error:" + method != null ? method.getName() : "", e);
        }
    }

    /**
     * @param obj   obj
     * @param field field
     * @param value value
     * @return T
     * @title set
     * @description
     * @author BiJi'an
     * @date 2023-02-11 01:07
     */
    public static void set(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new InitException("invoke error", e);
        }
    }

}

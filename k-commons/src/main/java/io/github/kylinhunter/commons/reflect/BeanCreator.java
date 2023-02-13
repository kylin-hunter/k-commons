package io.github.kylinhunter.commons.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class BeanCreator {

    /**
     * @param clazz          clazz
     * @param parameterTypes parameterTypes
     * @param parameters     parameters
     * @return T
     * @title create
     * @description
     * @author BiJi'an
     * @date 2022-12-03 20:44
     */
    public static <T> T createBean(Class<T> clazz, Class<?>[] parameterTypes, Object[] parameters) {
        try {
            Constructor<T> constructor = clazz.getConstructor(parameterTypes);
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new InitException("init createBean error", e);
        }

    }

    /**
     * @param clazz clazz
     * @return T
     * @title createBean
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:10
     */
    public static <T> T createBean(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new InitException("init createBean error", e);
        }

    }

    /**
     * @param constructor constructor
     * @param initargs    initargs
     * @return T
     * @title createBean
     * @description
     * @author BiJi'an
     * @date 2023-02-13 23:59
     */
    public static <T> T createBean(Constructor<T> constructor, Object... initargs) {
        try {
            return constructor.newInstance(initargs);
        } catch (Exception e) {
            throw new InitException("init createBean error", e);
        }

    }

    /**
     * @param method method
     * @param obj    obj
     * @param args   args
     * @return T
     * @title createBean1
     * @description
     * @author BiJi'an
     * @date 2023-02-14 00:06
     */
    public static <T> T createBean(Object obj, Method method, Object... args) {
        try {
            return ReflectUtil.invoke(obj, method, args);
        } catch (Exception e) {
            throw new InitException("init createBean error", e);
        }

    }
}

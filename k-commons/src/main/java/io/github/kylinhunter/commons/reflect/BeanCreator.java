package io.github.kylinhunter.commons.reflect;

import java.lang.reflect.Constructor;

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
     * @throws
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

    public static <T> T createBean(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new InitException("init createBean error", e);
        }

    }
}

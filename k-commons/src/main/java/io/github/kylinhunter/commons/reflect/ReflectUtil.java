package io.github.kylinhunter.commons.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-09 02:16
 **/
public class ReflectUtil {
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

    /**
     * @param clazz clazz
     * @param start start
     * @return java.lang.Class<?>
     * @title getGenericType
     * @description
     * @author BiJi'an
     * @date 2022-12-09 02:32
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericType(Class<?> clazz, int start) {
        Class<?>[] genericType = getGenericType(clazz, start, start + 1);
        if (genericType != null) {
            return (Class<T>) genericType[0];
        }
        return null;
    }

    /**
     * @param clazz clazz
     * @param start start
     * @param end   end
     * @return java.lang.Class<?>[]
     * @title getGenericType
     * @description
     * @author BiJi'an
     * @date 2022-12-09 02:30
     */
    public static Class<?>[] getGenericType(Class<?> clazz, int start, int end) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                int l = Math.min(end, actualTypeArguments.length);
                int s = Math.max(0, start);
                if (s < l) {
                    Class<?>[] result = new Class<?>[l - s];
                    int index = 0;
                    for (int i = s; i < l; i++) {
                        Type actualTypeArgument = actualTypeArguments[i];
                        result[index++] = (Class<?>) actualTypeArgument;
                    }
                    return result;
                }

            }
        }
        return null;
    }

    /**
     * @param type type
     * @return java.lang.reflect.ParameterizedType
     * @title toParameterizedType
     * @description
     * @author BiJi'an
     * @date 2023-01-21 01:01
     */
    public static ParameterizedType toParameterizedType(Type type) {
        if (type instanceof ParameterizedType) {
            return (ParameterizedType) type;
        }
        return null;

    }

    /**
     * @param parameterizedType parameterizedType
     * @return java.lang.Class<T>
     * @title getRawTypeClass
     * @description
     * @author BiJi'an
     * @date 2023-01-21 02:27
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getRawTypeClass(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (rawType instanceof Class<?>) {
            return (Class<T>) rawType;
        }
        return null;

    }

    /**
     * @param parameterizedType parameterizedType
     * @return java.lang.Class<?>[]
     * @title getActualTypeArgumentClasses
     * @description
     * @author BiJi'an
     * @date 2023-01-21 01:20
     */
    public static Class<?>[] getActualTypeArgumentClasses(ParameterizedType parameterizedType) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
            Class<?>[] classes = new Class[actualTypeArguments.length];
            for (int i = 0; i < actualTypeArguments.length; i++) {
                Type actualTypeArgument = actualTypeArguments[i];
                if (actualTypeArgument instanceof Class<?>) {

                    Class<?> actualTypeArgumentClazz = (Class<?>) actualTypeArgument;
                    classes[i] = actualTypeArgumentClazz;
                }
            }
            return classes;
        }
        return EMPTY_CLASS_ARRAY;

    }

    /**
     * @param parameterizedType parameterizedType
     * @param index             index
     * @return java.lang.Class<?>
     * @title getActualTypeArgumentClasses
     * @description
     * @author BiJi'an
     * @date 2023-01-21 02:08
     */
    public static Class<?> getActualTypeArgumentClasses(ParameterizedType parameterizedType, int index) {
        Class<?>[] actualTypeArgumentClasses = getActualTypeArgumentClasses(parameterizedType);
        if (index >= 0 && index < actualTypeArgumentClasses.length) {
            return actualTypeArgumentClasses[index];
        }
        return null;
    }
}

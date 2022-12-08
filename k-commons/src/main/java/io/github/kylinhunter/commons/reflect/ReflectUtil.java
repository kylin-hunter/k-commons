package io.github.kylinhunter.commons.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-09 02:16
 **/
public class ReflectUtil {

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
}

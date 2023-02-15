package io.github.kylinhunter.commons.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.reflect.bean.ActualType;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-09 02:16
 **/
public class GenericTypeUtils {
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    private static final ActualType[] EMPTY_ACTUAL_TYPE = new ActualType[0];

    /**
     * @param clazz clazz
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType
     * @title getSuperClassActualTypes
     * @description
     * @author BiJi'an
     * @date 2023-02-15 20:52
     */
    public static ActualType getSuperClassActualType(Class<?> clazz) {
        return getActualType(clazz.getGenericSuperclass());
    }

    /**
     * @param clazz clazz
     * @param index index
     * @return java.lang.Class<T>
     * @title getSuperClassActualType
     * @description
     * @author BiJi'an
     * @date 2023-02-15 23:18
     */
    public static <T> Class<T> getSuperClassActualType(Class<?> clazz, int index) {
        ActualType actualType = getActualType(clazz.getGenericSuperclass());
        if (actualType != null) {
            return actualType.getType(index);
        }
        return null;
    }

    /**
     * @param clazz clazz
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType[]
     * @title getInterfacesActualTypes
     * @description
     * @author BiJi'an
     * @date 2023-02-15 20:52
     */
    public static ActualType[] getInterfaceActualTypes(Class<?> clazz) {
        return getActualTypes(clazz.getGenericInterfaces());
    }

    /**
     * @param clazz          clazz
     * @param interfaceIndex interfaceIndex
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType
     * @title getInterfacesActualType
     * @description
     * @author BiJi'an
     * @date 2023-02-15 23:25
     */
    public static ActualType getInterfaceActualType(Class<?> clazz, int interfaceIndex) {
        ActualType[] actualTypes = getInterfaceActualTypes(clazz);
        if (interfaceIndex >= 0 && interfaceIndex < actualTypes.length) {
            return actualTypes[interfaceIndex];
        }
        return null;
    }

    /**
     * @param clazz          clazz
     * @param interfaceIndex interfaceIndex
     * @param typeIndex      typeIndex
     * @return java.lang.Class<T>
     * @title getInterfacesActualType
     * @description
     * @author BiJi'an
     * @date 2023-02-15 23:33
     */
    public static <T> Class<T> getInterfaceActualType(Class<?> clazz, int interfaceIndex, int typeIndex) {
        ActualType[] actualTypes = getInterfaceActualTypes(clazz);
        if (interfaceIndex >= 0 && interfaceIndex < actualTypes.length) {
            ActualType actualType = actualTypes[interfaceIndex];
            return actualType.getType(typeIndex);
        }
        return null;
    }

    /**
     * @param method method
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType
     * @title getMethodReturnActualTypes
     * @description
     * @author BiJi'an
     * @date 2023-02-15 20:52
     */
    public static ActualType getMethodReturnActualType(Method method) {
        return getActualType(method.getGenericReturnType());
    }

    /**
     * @param method method
     * @param index  index
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType
     * @title getMethodReturnActualType
     * @description
     * @author BiJi'an
     * @date 2023-02-15 23:37
     */
    public static <T> Class<T> getMethodReturnActualType(Method method, int index) {
        ActualType actualType = getActualType(method.getGenericReturnType());
        if (actualType != null) {
            return actualType.getType(index);
        }
        return null;
    }

    /**
     * @param method method
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType[]
     * @title getMethodParamterActualType
     * @description
     * @author BiJi'an
     * @date 2023-02-15 23:45
     */
    public static ActualType[] getMethodParamterActualType(Method method) {
        return getActualTypes(method.getGenericParameterTypes());
    }

    /**
     * @param method        method
     * @param paramterIndex paramterIndex
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType
     * @title getMethodParamterActualType
     * @description
     * @author BiJi'an
     * @date 2023-02-15 23:48
     */
    public static ActualType getMethodParamterActualType(Method method, int paramterIndex) {
        ActualType[] interfacesActualTypes = getMethodParamterActualType(method);
        if (paramterIndex >= 0 && paramterIndex < interfacesActualTypes.length) {
            return interfacesActualTypes[paramterIndex];
        }
        return null;
    }

    /**
     * @param method        method
     * @param paramterIndex paramterIndex
     * @param typeIndex     typeIndex
     * @return java.lang.Class<T>
     * @title getMethodParamterActualType
     * @description
     * @author BiJi'an
     * @date 2023-02-15 23:49
     */
    public static <T> Class<T> getMethodParamterActualType(Method method, int paramterIndex, int typeIndex) {
        ActualType[] actualTypes = getMethodParamterActualType(method);
        if (paramterIndex >= 0 && paramterIndex < actualTypes.length) {
            ActualType interfacesActualType = actualTypes[paramterIndex];
            return interfacesActualType.getType(typeIndex);
        }
        return null;
    }

    /**
     * @param type type
     * @return java.lang.Class<?>[]
     * @title getActualTypeArgumentClasses
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:23
     */

    public static Class<?>[] getActualTypeArguments(Type type) {
        ActualType actualType = getActualType(type);
        if (actualType != null) {
            return actualType.getTypes();
        }
        return EMPTY_CLASS_ARRAY;

    }

    /**
     * @param type  type
     * @param index index
     * @return java.lang.Class<?>
     * @title getActualTypeArgument
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:23
     */
    public static <T> Class<T> getActualTypeArgument(Type type, int index) {
        ActualType actualType = getActualType(type);
        if (actualType != null) {
            return actualType.getType(index);
        }
        return null;

    }

    /**
     * @param types types
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType
     * @title getActualTypes
     * @description
     * @author BiJi'an
     * @date 2023-02-16 00:16
     */
    private static ActualType[] getActualTypes(Type[] types) {
        if (types.length > 0) {
            ActualType[] actualTypes = new ActualType[types.length];
            for (int i = 0; i < types.length; i++) {
                actualTypes[i] = getActualType(types[i]);
            }
            return actualTypes;
        }
        return EMPTY_ACTUAL_TYPE;
    }

    /**
     * @param type type
     * @return io.github.kylinhunter.commons.reflect.bean.ActualType
     * @title getActualType
     * @description
     * @author BiJi'an
     * @date 2023-02-15 20:46
     */
    private static ActualType getActualType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() instanceof Class<?>) {
                Class<?> rawType = (Class<?>) parameterizedType.getRawType();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                    Class<?>[] clazzes = new Class[actualTypeArguments.length];
                    for (int i = 0; i < actualTypeArguments.length; i++) {
                        Type actualTypeArgument = actualTypeArguments[i];
                        if (actualTypeArgument instanceof Class<?>) {
                            clazzes[i] = (Class<?>) actualTypeArgument;

                        } else {
                            throw new GeneralException("not a class=>" + actualTypeArgument.getTypeName());
                        }
                    }
                    return new ActualType(rawType, clazzes);
                }
            }
        }
        return null;
    }

}

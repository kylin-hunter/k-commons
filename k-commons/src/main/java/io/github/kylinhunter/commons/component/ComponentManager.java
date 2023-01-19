package io.github.kylinhunter.commons.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.BeanCreator;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/

class ComponentManager {
    private final Map<Class<?>, Object> allComponents = Maps.newHashMap();
    private final Map<Class<?>, Set<Object>> allInterfaceComponents = Maps.newHashMap();
    private final ConstructorManager constructorManager;
    private final ComponentAssistant componentAssistant;

    public ComponentManager(ConstructorManager constructorManager) {
        this.constructorManager = constructorManager;
        this.componentAssistant = constructorManager.getComponentAssistant();
    }

    /**
     * @return void
     * @title clear
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:15
     */
    public void clear() {
        allComponents.clear();
        allInterfaceComponents.clear();
    }

    /**
     * @param allCConstructors allCConstructors
     * @return void
     * @title calComponents
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:27
     */
    public void calComponent(List<CConstructor> allCConstructors) {
        for (CConstructor cconstructor : allCConstructors) {
            calComponent(cconstructor);
        }

        Set<Class<?>> allComponentClazzes = constructorManager.getAllComponentClazzes();
        for (Class<?> componentClazz : allComponentClazzes) {
            Object component = allComponents.get(componentClazz);
            if (component != null) {
                Set<Class<?>> interfaces = componentAssistant.getAllInterface(componentClazz);
                for (Class<?> i : interfaces) {
                    allInterfaceComponents.compute(i, (k, v) -> {
                        if (v == null) {
                            v = Sets.newHashSet();
                        }
                        v.add(component);
                        return v;
                    });

                }
            }

        }
        if (allComponents.size() != allComponentClazzes.size()) {
            throw new InitException("no all  component be initialized ");

        }
    }

    public void calComponent(CConstructor cconstructor) {
        Constructor<?> constructor = cconstructor.getConstructor();
        Class<?> clazz = cconstructor.getClazz();
        int parameterCount = constructor.getParameterCount();
        if (parameterCount <= 0) {
            allComponents.put(clazz, BeanCreator.createBean(constructor));
        } else {

            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Type[] genericParameterTypes = constructor.getGenericParameterTypes();
            Object[] parameterObj = new Object[parameterCount];
            for (int i = 0; i < parameterCount; i++) {
                Class<?> parameterType = parameterTypes[i];
                Object obj = allComponents.get(parameterType);
                if (obj != null) {
                    parameterObj[i] = obj;
                } else {

                    Type type = genericParameterTypes[i];
                    if (type instanceof ParameterizedType) {

                        ParameterizedType parameterizedType = (ParameterizedType) type;
                        Type rawType = parameterizedType.getRawType();
                        if (rawType instanceof Class<?>) {
                            Class<?> rawTypeClazz = (Class<?>) rawType;
                            if (componentAssistant.isValidClazz(rawTypeClazz)) {
                                Set<CConstructor> cconstructors =
                                        constructorManager.getCConstructorByInterface(rawTypeClazz);
                                if (cconstructors != null) {
                                    parameterObj[i] = allComponents.get(cconstructors.iterator().next().getClazz());
                                }
                            } else if (List.class.isAssignableFrom(rawTypeClazz)) {
                                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                                for (Type actualTypeArgument : actualTypeArguments) {
                                    if (actualTypeArgument instanceof Class<?>) {
                                        Class<?> actualTypeArgumentClazz = (Class<?>) actualTypeArgument;

                                        Set<CConstructor> cconstructors =
                                                constructorManager.getCConstructorByInterface(actualTypeArgumentClazz);
                                        List<Object> objs = Lists.newArrayList();
                                        if (cconstructors != null) {
                                            for (CConstructor tmpCConstructor : cconstructors) {
                                                Object tmpObj = allComponents.get(tmpCConstructor.getClazz());
                                                if (tmpObj != null) {
                                                    objs.add(tmpObj);
                                                }
                                            }
                                        }
                                        if (objs.size() > 0) {
                                            parameterObj[i] = objs;
                                        }
                                    }

                                }
                            }

                        }

                    }

                    if (parameterObj[i] == null) {
                        throw new InitException(
                                "no component:" + clazz.getName() + "/" + parameterType.getName());

                    }

                }
            }

            try {
                allComponents.put(clazz, constructor.newInstance(parameterObj));
            } catch (Exception e) {
                throw new InitException("init constructor error:" + clazz.getName(), e);
            }
        }
    }

    /**
     * @param clazz clazz
     * @return java.lang.Object
     * @title getComponent
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:22
     */
    public Object getComponent(Class<?> clazz) {
        return this.allComponents.get(clazz);
    }
}

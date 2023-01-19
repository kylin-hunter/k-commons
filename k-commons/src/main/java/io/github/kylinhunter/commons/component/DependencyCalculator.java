package io.github.kylinhunter.commons.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.exception.embed.InitException;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 02:19
 **/

@Data
public class DependencyCalculator {
    private final Map<Class<?>, Set<Class<?>>> allDependencies = Maps.newHashMap();
    private ConstructorManager cconstructorManager;
    private ComponentAssistant componentAssistant;

    public DependencyCalculator(ConstructorManager cconstructorManager) {
        this.cconstructorManager = cconstructorManager;
        this.componentAssistant = cconstructorManager.getComponentAssistant();
    }

    /**
     * @return void
     * @title clear
     * @description
     * @author BiJi'an
     * @date 2023-01-20 01:06
     */
    public void clear() {
        allDependencies.clear();
    }

    /**
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:25
     */
    public void calDependencies() {
        for (CConstructor cconstructor : cconstructorManager.getAllCConstructors().values()) {
            this.calDependencies(cconstructor);
        }
    }

    /**
     * @param constructor constructor
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:06
     */
    private void calDependencies(CConstructor constructor) {
        Class<?> clazz = constructor.getClazz();
        calDependencies(clazz, clazz, null);
        constructor.setDepLevel(allDependencies.get(clazz).size());
    }

    /**
     * @param oriClazz oriClazz
     * @param depClazz depClazz
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:21
     */
    public void calDependencies(Class<?> oriClazz, Class<?> depClazz, Type genericParameterType) {
        Set<CConstructor> allCConstructors = Sets.newHashSet();
        if (componentAssistant.isValidClazz(depClazz)) {
            CConstructor existCConstructor = cconstructorManager.getCConstructor(depClazz);
            if (existCConstructor != null) {
                allCConstructors.add(existCConstructor);
            }
        }

        if (genericParameterType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class<?>) {
                Class<?> rawTypeClazz = (Class<?>) rawType;
                if (componentAssistant.isValidClazz(rawTypeClazz)) {
                    Set<CConstructor> cconstructors = cconstructorManager.getCConstructorByInterface(rawTypeClazz);
                    if (cconstructors != null) {
                        allCConstructors.addAll(cconstructors);
                    }

                } else if (List.class.isAssignableFrom(rawTypeClazz)) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (Type actualTypeArgument : actualTypeArguments) {
                        if (actualTypeArgument instanceof Class<?>) {
                            Class<?> actualTypeArgumentClazz = (Class<?>) actualTypeArgument;
                            Set<CConstructor> cconstructors =
                                    cconstructorManager.getCConstructorByInterface(actualTypeArgumentClazz);
                            if (cconstructors != null) {
                                allCConstructors.addAll(cconstructors);
                            }
                        }

                    }
                }

            }
        }

        if (allCConstructors.size() <= 0) {
            throw new InitException("no exist CConstructor for :" + depClazz.getName());
        }
        for (CConstructor cconstructor : allCConstructors) {
            allDependencies.compute(oriClazz, (k, v) -> {
                if (v == null) {
                    v = Sets.newHashSet();
                }
                v.add(cconstructor.getClazz());
                return v;
            });

            Constructor<?> constructor = cconstructor.getConstructor();
            if (constructor.getParameterCount() > 0) {
                Type[] genericParameterTypes = constructor.getGenericParameterTypes();
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    calDependencies(oriClazz, parameterTypes[i], genericParameterTypes[i]);
                }

            }
        }

    }
}


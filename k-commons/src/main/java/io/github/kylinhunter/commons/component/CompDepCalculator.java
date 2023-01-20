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
public class CompDepCalculator {
    private final Map<Class<?>, Set<Class<?>>> allDependencies = Maps.newHashMap();
    private CompConstructorManager compConstructorManager;
    private CompTools compTools;

    public CompDepCalculator(CompConstructorManager compConstructorManager) {
        this.compConstructorManager = compConstructorManager;
        this.compTools = compConstructorManager.getCompTools();
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
        for (CompConstructor compConstructor : compConstructorManager.getCompConstructors().values()) {
            this.calDependencies(compConstructor);
        }
    }

    /**
     * @param compConstructor compConstructor
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:06
     */
    private void calDependencies(CompConstructor compConstructor) {
        Class<?> compClazz = compConstructor.getCompClazz();
        calDependencies(compClazz, compClazz, null);
        compConstructor.setDepLevel(allDependencies.get(compClazz).size());
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
    private void calDependencies(Class<?> oriClazz, Class<?> depClazz, Type genericParameterType) {
        Set<CompConstructor> allCompConstructors = Sets.newHashSet();
        if (compTools.isValidClazz(depClazz)) {
            CompConstructor existCompConstructor = compConstructorManager.getCompConstructor(depClazz);
            if (existCompConstructor != null) {
                allCompConstructors.add(existCompConstructor);
            }
        }

        if (genericParameterType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class<?>) {
                Class<?> rawTypeClazz = (Class<?>) rawType;
                if (compTools.isValidClazz(rawTypeClazz)) {
                    Set<CompConstructor> cconstructors = compConstructorManager.getCompConstructorByInterface(rawTypeClazz);
                    if (cconstructors != null) {
                        allCompConstructors.addAll(cconstructors);
                    }

                } else if (List.class.isAssignableFrom(rawTypeClazz)) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (Type actualTypeArgument : actualTypeArguments) {
                        if (actualTypeArgument instanceof Class<?>) {
                            Class<?> actualTypeArgumentClazz = (Class<?>) actualTypeArgument;
                            Set<CompConstructor> cconstructors =
                                    compConstructorManager.getCompConstructorByInterface(actualTypeArgumentClazz);
                            if (cconstructors != null) {
                                allCompConstructors.addAll(cconstructors);
                            }
                        }

                    }
                }

            }
        }

        if (allCompConstructors.size() <= 0) {
            throw new InitException("no exist CompConstructor for :" + depClazz.getName());
        }
        for (CompConstructor cconstructor : allCompConstructors) {
            allDependencies.compute(oriClazz, (k, v) -> {
                if (v == null) {
                    v = Sets.newHashSet();
                }
                v.add(cconstructor.getCompClazz());
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


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
import io.github.kylinhunter.commons.reflect.ReflectUtil;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 02:19
 **/

@Data
public class CompDepCalculator {
    private final Map<Class<?>, Set<Class<?>>> allDependencies = Maps.newHashMap();
    private CompConstructorManager constructorManager;
    private CompTools compTools;

    public CompDepCalculator(CompConstructorManager constructorManager) {
        this.constructorManager = constructorManager;
        this.compTools = constructorManager.getCompTools();
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
        for (CompConstructor compConstructor : constructorManager.getAll().values()) {
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
        Class<?> compClazz = compConstructor.getClazz();
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
        if (compTools.isValid(depClazz)) {
            CompConstructor existCompConstructor = constructorManager.get(depClazz);
            if (existCompConstructor != null) {
                allCompConstructors.add(existCompConstructor);
            }
        }

        ParameterizedType parameterizedType = ReflectUtil.toParameterizedType(genericParameterType);
        if (parameterizedType != null) {
            Class<?> rawTypeClazz = ReflectUtil.getRawTypeClass(parameterizedType);
            if (rawTypeClazz != null) {
                if (compTools.isValid(rawTypeClazz)) {
                    allCompConstructors.addAll(constructorManager.getAllByInterface(rawTypeClazz));

                } else if (List.class.isAssignableFrom(rawTypeClazz)) {
                    Class<?>[] actualTypeArgumentClasses = ReflectUtil.getActualTypeArgumentClasses(parameterizedType);
                    for (Class<?> actualTypeArgumentClass : actualTypeArgumentClasses) {
                        allCompConstructors.addAll(constructorManager.getAllByInterface(actualTypeArgumentClass));
                    }
                }
            }

        }

        if (allCompConstructors.size() <= 0) {
            throw new InitException("no exist CompConstructor for :" + depClazz.getName());
        }
        for (CompConstructor compConstructor : allCompConstructors) {
            allDependencies.compute(oriClazz, (k, v) -> {
                if (v == null) {
                    v = Sets.newHashSet();
                }
                v.add(compConstructor.getClazz());
                return v;
            });

            Constructor<?> constructor = compConstructor.getConstructor();
            if (constructor.getParameterCount() > 0) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Type[] genericParameterTypes = constructor.getGenericParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    calDependencies(oriClazz, parameterTypes[i], genericParameterTypes[i]);
                }

            }
        }

    }
}


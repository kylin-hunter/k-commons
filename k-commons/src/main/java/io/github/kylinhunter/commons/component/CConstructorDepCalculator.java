package io.github.kylinhunter.commons.component;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 02:19
 **/

public class CConstructorDepCalculator {
    private final Map<CConstructor, Set<CConstructor>> dependencies = MapUtils.newHashMap();
    private final CConstructorManager constructorManager;

    public CConstructorDepCalculator(CConstructorManager constructorManager) {
        this.constructorManager = constructorManager;
    }

    /**
     * @return void
     * @title clear
     * @description
     * @author BiJi'an
     * @date 2023-01-20 01:06
     */
    public void clean() {
        dependencies.clear();
    }

    /**
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:25
     */
    public List<CConstructor> calculate(List<CConstructor> constructors) {
        this.clean();
        for (CConstructor cconstructor : constructors) {
            this.calculate(cconstructor);
        }
        return constructors.stream().sorted(Comparator.comparingInt(CConstructor::getDepLevel))
                .collect(Collectors.toList());
    }

    /**
     * @param cconstructor cconstructor
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:06
     */
    private void calculate(CConstructor cconstructor) {
        calculate(cconstructor, SetUtils.newHashSet(cconstructor));
        cconstructor.setDepLevel(dependencies.get(cconstructor).size());
    }

    /**
     * @param oriCConstructor  oriCConstructor
     * @param depCConstructors depCConstructors
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:21
     */
    private void calculate(CConstructor oriCConstructor, Set<CConstructor> depCConstructors) {

        if (depCConstructors.size() <= 0) {
            return;
        }
        for (CConstructor curConstructor : depCConstructors) {
            dependencies.compute(oriCConstructor, (k, v) -> {
                if (v == null) {
                    v = SetUtils.newHashSet();
                }
                v.add(curConstructor);
                return v;
            });

            Constructor<?> constructor = curConstructor.getConstructor();
            if (constructor.getParameterCount() > 0) {
                Set<CConstructor> tmpCConstructors = SetUtils.newHashSet();
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Type[] genericParameterTypes = constructor.getGenericParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> curParametorClass = parameterTypes[i];
                    if (Collection.class.isAssignableFrom(curParametorClass)) {
                        Type type = genericParameterTypes[i];
                        Class<?>[] actualTypeArgumentClasses = GenericTypeUtils.getActualTypeArguments(type);
                        for (Class<?> actualTypeArgumentClass : actualTypeArgumentClasses) {
                            tmpCConstructors.addAll(constructorManager.getAll(actualTypeArgumentClass));
                        }
                    } else {
                        tmpCConstructors.add(constructorManager.get(curParametorClass, true));
                    }
                    calculate(oriCConstructor, tmpCConstructors);
                }

            }
        }

    }
}

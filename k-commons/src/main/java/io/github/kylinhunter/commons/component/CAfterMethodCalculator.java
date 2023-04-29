package io.github.kylinhunter.commons.component;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.reflections.ReflectionUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/

@RequiredArgsConstructor
class CAfterMethodCalculator {

    private CConstructorManager constructorManager;
    private CMethodManager methodManager;
    private CompManager compManager;

    public CAfterMethodCalculator(CompManager compManager) {
        this.compManager = compManager;
        this.constructorManager = compManager.constructorCompManager.constructorManager;
        this.methodManager = compManager.methodCompManager.methodManager;
    }

    /**
     * @return void
     * @title calComponents
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:27
     */
    public void calculate() {

        List<CAfterMethod> allCAfterMethods = this.getAllCAfterMethods();

        for (CAfterMethod afterMethod : allCAfterMethods) {
            ReflectUtils.invoke(afterMethod.getCompObject(), afterMethod.getMethod());
        }
    }

    /**
     * @return java.util.Set<java.lang.Class < ?>>
     * @title getAllCompClasses
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:42
     */
    @SuppressWarnings("unchecked")
    private List<CAfterMethod> getAllCAfterMethods() {
        List<CAfterMethod> allCAfterMethods = ListUtils.newArrayList();
        Set<Class<?>> allCompClasses = SetUtils.newHashSet(constructorManager.getCompClasses());
        allCompClasses.addAll(methodManager.getCompClasses());
        for (Class<?> compClass : allCompClasses) {
            Object o = compManager.get(compClass, true);
            Set<Method> methods =
                    ReflectionUtils.getAllMethods(compClass, (m) -> m.getAnnotation(CAfter.class) != null);
            if (!CollectionUtils.isEmpty(methods)) {
                Set<CAfterMethod> cafterMethods = methods.stream().
                        map(m -> new CAfterMethod(m, o, m.getAnnotation(CAfter.class))).collect(Collectors.toSet());
                allCAfterMethods.addAll(cafterMethods);
            }
        }
        return allCAfterMethods;
    }

}
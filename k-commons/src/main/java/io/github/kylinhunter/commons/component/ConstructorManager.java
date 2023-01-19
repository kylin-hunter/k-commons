package io.github.kylinhunter.commons.component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/

class ConstructorManager {
    @Getter
    private final Map<Class<?>, CConstructor> allCConstructors = Maps.newHashMap();
    @Getter
    private final Map<Class<?>, Set<CConstructor>> allInterfaceCconstructors = Maps.newHashMap();
    @Getter
    private Set<Class<?>> allComponentClazzes;

    @Getter
    private final ComponentAssistant componentAssistant;
    @Getter
    private final DependencyCalculator dependencyCalculator;

    public ConstructorManager(ComponentAssistant componentAssistant) {
        this.componentAssistant = componentAssistant;
        this.dependencyCalculator = new DependencyCalculator(this);
    }

    /**
     * @return void
     * @title clear
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:14
     */
    public void clear() {
        allCConstructors.clear();
        allInterfaceCconstructors.clear();
        if (allComponentClazzes != null) {
            allComponentClazzes.clear();
        }
        dependencyCalculator.clear();
    }

    /**
     * @return java.util.List<io.github.kylinhunter.commons.component.CConstructor>
     * @title initConstructors
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:57
     */
    public List<CConstructor> initConstructors() {
        this.allComponentClazzes = componentAssistant.calAllComponentClazzes();
        for (Class<?> clazz : allComponentClazzes) {
            this.calConstructor(clazz);
        }
        dependencyCalculator.calDependencies();
        return allCConstructors.values().stream().sorted(Comparator.comparingInt(CConstructor::getDepLevel))
                .collect(Collectors.toList());

    }

    /**
     * @param clazz clazz
     * @return void
     * @title calConstructor
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:53
     */
    private void calConstructor(Class<?> clazz) {
        CConstructor cconstructor = new CConstructor(clazz, clazz.getConstructors()[0]);
        allCConstructors.put(clazz, cconstructor);

        Set<Class<?>> interfaces = componentAssistant.getAllInterface(clazz);
        for (Class<?> interfaceClazz : interfaces) {

            allInterfaceCconstructors.compute(interfaceClazz, (k, v) -> {
                if (v == null) {
                    v = Sets.newHashSet();
                }
                v.add(cconstructor);
                return v;
            });
        }
    }

    /**
     * @param clazz clazz
     * @return io.github.kylinhunter.commons.component.CConstructor
     * @title get
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:56
     */
    public CConstructor getCConstructor(Class<?> clazz) {
        return allCConstructors.get(clazz);
    }

    /**
     * @param clazz clazz
     * @return java.util.Set<io.github.kylinhunter.commons.component.CConstructor>
     * @title getCConstructorByInterface
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:56
     */
    public Set<CConstructor> getCConstructorByInterface(Class<?> clazz) {
        return allInterfaceCconstructors.get(clazz);
    }
}

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

class CompConstructorManager {
    @Getter
    private final Map<Class<?>, CompConstructor> compConstructors = Maps.newHashMap();
    @Getter
    private final Map<Class<?>, Set<CompConstructor>> interfaceCompConstructors = Maps.newHashMap();
    @Getter
    private final Set<Class<?>> compClazzes = Sets.newHashSet();

    @Getter
    private final CompTools compTools;
    private final CompDepCalculator compDepCalculator;

    public CompConstructorManager(CompTools compTools) {
        this.compTools = compTools;
        this.compDepCalculator = new CompDepCalculator(this);
    }

    /**
     * @return void
     * @title clear
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:14
     */
    public void clear() {
        compConstructors.clear();
        interfaceCompConstructors.clear();
        compClazzes.clear();
        compDepCalculator.clear();
    }

    /**
     * @return java.util.List<io.github.kylinhunter.commons.component.CompConstructor>
     * @title initConstructors
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:57
     */
    public List<CompConstructor> calCompConstructor() {
        this.compClazzes.addAll(compTools.calAllCompClazzes());
        for (Class<?> compClazz : this.compClazzes) {
            this.calCompConstructor(compClazz);
        }
        compDepCalculator.calDependencies();
        return compConstructors.values().stream().sorted(Comparator.comparingInt(CompConstructor::getDepLevel))
                .collect(Collectors.toList());

    }

    /**
     * @param compClazz compClazz
     * @return void
     * @title calConstructor
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:53
     */
    private void calCompConstructor(Class<?> compClazz) {
        CompConstructor compConstructor = new CompConstructor(compClazz, compClazz.getConstructors()[0]);
        compConstructors.put(compClazz, compConstructor);

        Set<Class<?>> interfaces = compTools.getAllInterface(compClazz);
        for (Class<?> interfaceClazz : interfaces) {
            interfaceCompConstructors.compute(interfaceClazz, (k, v) -> {
                if (v == null) {
                    v = Sets.newHashSet();
                }
                v.add(compConstructor);
                return v;
            });
        }
    }

    /**
     * @param componentClazz componentClazz
     * @return io.github.kylinhunter.commons.component.CompConstructor
     * @title get
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:56
     */
    public CompConstructor getCompConstructor(Class<?> componentClazz) {
        return compConstructors.get(componentClazz);
    }

    /**
     * @param interfaceClazz interfaceClazz
     * @return java.util.Set<io.github.kylinhunter.commons.component.CompConstructor>
     * @title getCConstructorByInterface
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:56
     */
    public Set<CompConstructor> getCompConstructorByInterface(Class<?> interfaceClazz) {
        return interfaceCompConstructors.get(interfaceClazz);
    }
}

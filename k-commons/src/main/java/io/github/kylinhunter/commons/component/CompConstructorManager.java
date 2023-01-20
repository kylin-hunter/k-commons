package io.github.kylinhunter.commons.component;

import java.util.Collections;
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

    private final Map<Class<?>, CompConstructor> compConstructors = Maps.newHashMap();

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
     * @return java.util.Map<java.lang.Class < ?>,io.github.kylinhunter.commons.component.CompConstructor>
     * @title getAll
     * @description
     * @author BiJi'an
     * @date 2023-01-21 02:15
     */
    public Map<Class<?>, CompConstructor> getAll() {
        return compConstructors;
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
     * @param compClazz clazz
     * @return void
     * @title calConstructor
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:53
     */
    private void calCompConstructor(Class<?> compClazz) {
        C c = compClazz.getAnnotation(C.class);
        CompConstructor compConstructor = new CompConstructor(compClazz, c.primary(), compClazz.getConstructors()[0]);
        compConstructors.put(compClazz, compConstructor);
        Set<Class<?>> interfaces = compTools.getAllInterface(compClazz);
        for (Class<?> interfaceClazz : interfaces) {
            interfaceCompConstructors.compute(interfaceClazz, (k, v) -> {
                if (v == null) {
                    v = Sets.newTreeSet(Comparator.comparing((constructor) -> constructor.getClazz().getName()));
                }
                v.add(compConstructor);
                return v;
            });
        }
    }

    /**
     * @param compClazz compClazz
     * @return io.github.kylinhunter.commons.component.CompConstructor
     * @title get
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:56
     */
    public CompConstructor get(Class<?> compClazz) {
        return compConstructors.get(compClazz);
    }

    /**
     * @param interfaceClazz interfaceClazz
     * @return java.util.Set<io.github.kylinhunter.commons.component.CompConstructor>
     * @title getCConstructorByInterface
     * @description
     * @author BiJi'an
     * @date 2023-01-19 23:56
     */
    public Set<CompConstructor> getAllByInterface(Class<?> interfaceClazz) {
        Set<CompConstructor> compConstructors = interfaceCompConstructors.get(interfaceClazz);
        if (compConstructors != null) {
            return compConstructors;
        } else {
            return Collections.emptySet();
        }
    }

    public CompConstructor getByInterface(Class<?> interfaceClazz) {
        Set<CompConstructor> compConstructors = interfaceCompConstructors.get(interfaceClazz);
        if (compConstructors != null && compConstructors.size() > 0) {
            CompConstructor firstCompConstructor = null;
            for (CompConstructor compConstructor : compConstructors) {
                if (firstCompConstructor == null) {
                    firstCompConstructor = compConstructor;
                }
                if (compConstructor.isPrimary()) {
                    return compConstructor;
                }

            }
            return firstCompConstructor;

        }
        return null;

    }
}

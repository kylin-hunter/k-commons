package io.github.kylinhunter.commons.component;

import java.util.Set;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import com.google.common.collect.Sets;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 02:19
 **/

@Data
public class ComponentAssistant {
    private String[] pkgs;

    public ComponentAssistant(String... pkgs) {
        this.pkgs = pkgs;
    }

    /**
     * @param clazz clazz
     * @return java.util.Set<java.lang.Class < ?>>
     * @title getAllInterface
     * @description
     * @author BiJi'an
     * @date 2023-01-19 02:43
     */
    @SuppressWarnings("unchecked")
    public Set<Class<?>> getAllInterface(Class<?> clazz) {
        return ReflectionUtils.getAllSuperTypes(clazz, c -> c.isInterface() && isValidClazz(c));
    }

    /**
     * @param clazz clazz
     * @return boolean
     * @title isValid
     * @description
     * @author BiJi'an
     * @date 2023-01-19 22:07
     */
    public boolean isValidClazz(Class<?> clazz) {
        for (String pkg : pkgs) {
            if (clazz.getPackage().getName().contains(pkg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return java.util.Set<java.lang.Class < ?>>
     * @title getAllComponentClazzes
     * @description
     * @author BiJi'an
     * @date 2023-01-19 02:59
     */
    public Set<Class<?>> calAllComponentClazzes() {
        Set<Class<?>> allComponentClazzes = Sets.newHashSet();
        for (String pkg : pkgs) {
            Reflections reflections = new Reflections(pkg, Scanners.TypesAnnotated);
            allComponentClazzes = reflections.getTypesAnnotatedWith(C.class);
        }
        return allComponentClazzes;
    }
}

package io.github.kylinhunter.commons.component;

import java.lang.annotation.Annotation;
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
public class CompTools {
    private String[] pkgs;

    public CompTools(String... pkgs) {
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
        return ReflectionUtils.getAllSuperTypes(clazz, c -> c.isInterface() && isValid(c));
    }

    /**
     * @param clazz clazz
     * @return boolean
     * @title isValid
     * @description
     * @author BiJi'an
     * @date 2023-01-19 22:07
     */
    public boolean isValid(Class<?> clazz) {
        for (String pkg : pkgs) {
            if (clazz.getPackage().getName().contains(pkg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return java.util.Set<java.lang.Class < ?>>
     * @title getCompClazzes
     * @description
     * @author BiJi'an
     * @date 2023-01-19 02:59
     */
    public Set<Class<?>> calAllCompClazzes() {
        return getTypesAnnotatedWith(C.class);
    }

    /**
     * @return java.util.Set<java.lang.Class < ?>>
     * @title calAllCompMethodClazzes
     * @description
     * @author BiJi'an
     * @date 2023-02-10 00:49
     */
    public Set<Class<?>> calAllCompMethodClazzes() {
        return getTypesAnnotatedWith(CC.class);
    }

    /**
     * @param annotation annotation
     * @return java.util.Set<java.lang.Class < ?>>
     * @title getTypesAnnotatedWith
     * @description
     * @author BiJi'an
     * @date 2023-02-09 23:26
     */
    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        Set<Class<?>> allCompClazzes = Sets.newHashSet();
        for (String pkg : pkgs) {
            Reflections reflections = new Reflections(pkg, Scanners.TypesAnnotated);
            allCompClazzes = reflections.getTypesAnnotatedWith(annotation);
        }
        return allCompClazzes;
    }
}

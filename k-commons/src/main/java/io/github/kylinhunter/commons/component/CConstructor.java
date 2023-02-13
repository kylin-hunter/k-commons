package io.github.kylinhunter.commons.component;

import java.lang.reflect.Constructor;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class CConstructor {
    @EqualsAndHashCode.Include
    private Class<?> clazz;
    private Constructor<?> constructor;
    private boolean primary;
    private int depLevel;

    public CConstructor(Class<?> clazz, boolean primary) {
        this.clazz = clazz;
        this.constructor = clazz.getConstructors()[0];
        this.primary = primary;
    }

}
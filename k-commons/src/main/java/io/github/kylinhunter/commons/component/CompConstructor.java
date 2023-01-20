package io.github.kylinhunter.commons.component;

import java.lang.reflect.Constructor;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/
@Data
class CompConstructor {
    private Class<?> clazz;
    private boolean primary;
    private Constructor<?> constructor;
    private int depLevel;

    public CompConstructor(Class<?> clazz, boolean primary, Constructor<?> constructor) {

        this.clazz = clazz;
        this.primary = primary;
        this.constructor = constructor;
    }

}

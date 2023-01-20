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
    private Class<?> compClazz;
    private Constructor<?> constructor;
    private int depLevel;

    public CompConstructor(Class<?> compClazz, Constructor<?> constructor) {
        this.compClazz = compClazz;
        this.constructor = constructor;
    }

}

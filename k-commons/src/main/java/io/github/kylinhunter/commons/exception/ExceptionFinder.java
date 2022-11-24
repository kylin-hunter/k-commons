package io.github.kylinhunter.commons.exception;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 01:46
 **/
public class ExceptionFinder {

    /**
     * @param throwable      throwable
     * @param acceptSubClass acceptSubClass
     * @param cls            cls
     * @return T
     * @title find source
     * @description
     * @author BiJi'an
     * @date 2022/01/01 5:14 下午
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T find(Throwable throwable, boolean acceptSubClass, Class<T> cls) {

        if (cls != null) {
            while (throwable != null) {
                if (throwable.getClass().equals(cls)) {
                    return (T) throwable;
                } else if (acceptSubClass && cls.isAssignableFrom(throwable.getClass())) {
                    return (T) throwable;
                }
                throwable = throwable.getCause();
            }
        }
        return null;
    }

    /**
     * @param throwable      throwable
     * @param acceptSubClass acceptSubClass
     * @param samples        samples
     * @return io.github.kylinhunter.commons.source.ExceptionHelper.ExceptionFind
     * @title find first source
     * @description
     * @author BiJi'an
     * @date 2022/01/01 5:20 下午
     */
    public static ExceptionFind find(Throwable throwable, boolean acceptSubClass,
                                     Set<Class<? extends Throwable>> samples) {

        if (samples != null) {
            while (throwable != null) {
                for (Class<? extends Throwable> sample : samples) {
                    if (throwable.getClass().equals(sample)) {
                        return new ExceptionFind(throwable, sample);
                    }
                    if (acceptSubClass && sample.isAssignableFrom(throwable.getClass())) {
                        return new ExceptionFind(throwable, sample);
                    }
                }

                throwable = throwable.getCause();
            }
        }

        return null;

    }

    @AllArgsConstructor
    @Getter
    public static class ExceptionFind {
        private final Throwable target;
        private final Class<? extends Throwable> source;

    }
}



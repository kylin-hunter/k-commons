package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import java.util.Objects;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:05
 **/
public class ClassUtil {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String className) {
        try {
            Objects.requireNonNull(className);
            return (Class<T>) Class.forName(className);
        } catch (Exception e) {
            throw new GeneralException("loadClass error ", e);
        }
    }
}

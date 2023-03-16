package io.github.kylinhunter.commons.exception.check;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.exception.embed.ParamException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/10/22
 **/
public class ExceptionChecker {

    /**
     * @param reference    reference
     * @param errorMessage errorMessage
     * @return T
     * @title checkNotNull
     * @description
     * @author BiJi'an
     * @date 2022-10-22 22:00
     */
    public static <T> T checkNotNull(T reference, String errorMessage) {
        if (reference == null) {
            throw new ParamException(errorMessage);
        }
        return reference;
    }

    /**
     * @param collection   collection
     * @param errorMessage errorMessage
     * @return void
     * @throws
     * @title checkNotEmpty
     * @description
     * @author BiJi'an
     * @date 2022-10-22 22:01
     */
    public static void checkNotEmpty(Collection<?> collection, String errorMessage) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ParamException(errorMessage);
        }
    }

    /**
     * @param name         name
     * @param errorMessage errorMessage
     * @return void
     * @title checkNotEmpty
     * @description
     * @author BiJi'an
     * @date 2023-01-06 00:31
     */
    public static void checkNotEmpty(String name, String errorMessage) {
        if (StringUtils.isEmpty(name)) {
            throw new ParamException(errorMessage);
        }
    }

    /**
     * @param expression   expression
     * @param errorMessage errorMessage
     * @return void
     * @title checkArgument
     * @description
     * @author BiJi'an
     * @date 2022-10-22 22:01
     */
    public static void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new ParamException(String.valueOf(errorMessage));
        }
    }

    /**
     * @param value
     * @param name
     * @return void
     * @title checkNonnegative
     * @description
     * @author BiJi'an
     * @date 2023-03-18 22:55
     */
    public static void checkNonnegative(int value, String name) {
        if (value < 0) {
            throw new ParamException(name + " cannot be negative but was: " + value);
        }
    }

}

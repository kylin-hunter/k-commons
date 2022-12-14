package io.github.kylinhunter.commons.exception.check;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

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

}

package io.github.kylinhunter.commons.cache;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.exception.embed.ParamException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-08-17 10:36
 **/
public class GuavaStringKeyHelper {

    private static final String DELIMITER = ",";

    /**
     * @param params params
     * @return java.lang.String
     * @title toKey
     * @description
     * @author BiJi'an
     * @date 2022-08-17 14:20
     */
    public static String toKey(Object... params) {
        if (params.length == 0) {
            throw new ParamException("can't empty");
        } else if (params.length == 1) {
            return String.valueOf(params[0]);
        } else if (params.length == 2) {
            return params[0] + DELIMITER + params[1];

        } else {
            StringJoiner joiner = new StringJoiner(DELIMITER);
            for (Object param : params) {
                joiner.add(String.valueOf(param));
            }
            return joiner.toString();
        }

    }

    /**
     * @param key key
     * @return java.lang.String[]
     * @title toParams
     * @description
     * @author BiJi'an
     * @date 2022-08-17 14:20
     */
    public static String[] toParams(String key) {
        return StringUtils.split(key, DELIMITER);
    }
}

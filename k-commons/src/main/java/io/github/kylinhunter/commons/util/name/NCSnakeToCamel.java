package io.github.kylinhunter.commons.util.name;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.util.StringPool;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
public class NCSnakeToCamel extends NCSnakeToCamelUpperFirst {

    @Override
    public String convert(String name) {
        if (StringUtils.isBlank(name)) {
            return StringPool.EMPTY;
        }

        StringBuilder stringBuilder = snakeToCamel(name);
        stringBuilder.setCharAt(0, Character.toLowerCase(stringBuilder.charAt(0)));
        return stringBuilder.toString();
    }
}

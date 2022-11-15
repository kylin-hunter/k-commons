package com.kylinhunter.plat.commons.util.name;

import jodd.util.StringPool;
import jodd.util.StringUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
public class NCSnakeToCamel extends NCSnakeToCamelUpperFirst {

    @Override
    public String convert(String name) {
        if (StringUtil.isBlank(name)) {
            return StringPool.EMPTY;
        }

        StringBuilder stringBuilder = snakeToCamel(name);
        stringBuilder.setCharAt(0, Character.toLowerCase(stringBuilder.charAt(0)));
        return stringBuilder.toString();
    }
}

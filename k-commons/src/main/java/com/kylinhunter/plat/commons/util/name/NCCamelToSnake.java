package com.kylinhunter.plat.commons.util.name;

import org.apache.commons.lang3.StringUtils;

import jodd.util.StringPool;

/**
 * @description
 * @author BiJi'an
 * @date   2022-01-01 14:39
 **/
public class NCCamelToSnake implements NamingConvertor {
    public String convert(String name) {

        if (StringUtils.isBlank(name)) {
            return StringPool.EMPTY;
        }
        int len = name.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(StringPool.UNDERSCORE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

}

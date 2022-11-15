package com.kylinhunter.plat.commons.util.name;

import java.util.Arrays;

import jodd.util.StringPool;
import jodd.util.StringUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
public class NCSnakeToCamelUpperFirst implements NamingConvertor {
    public String convert(String name) {
        if (StringUtil.isBlank(name)) {
            return StringPool.EMPTY;
        }

        return snakeToCamel(name).toString();

    }

    protected StringBuilder snakeToCamel(String name) {
        StringBuilder result = new StringBuilder();
        String[] camels = name.split(StringPool.UNDERSCORE);
        // 处理驼峰片段
        Arrays.stream(camels).filter(camel -> !StringUtil.isBlank(camel)).forEach(camel -> {
            result.append(Character.toUpperCase(camel.charAt(0)) + camel.substring(1).toLowerCase());
        });
        return result;
    }

}

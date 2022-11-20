package io.github.kylinhunter.commons.util.name;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.internal.StringUtil;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.util.StringPool;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
@C
@Data
public class NCSnakeToCamelUpperFirst implements NamingConvertor {
    private NCStrategy ncStrategy = NCStrategy.SNAKE_TO_CAMEL;

    public String convert(String name) {
        if (StringUtils.isBlank(name)) {
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

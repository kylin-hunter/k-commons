package io.github.kylinhunter.commons.name;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.strings.StringConst;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
@C
@Data
public class NCSnakeToCamelUpperFirst implements NameConvertor {
    private NCStrategy ncStrategy = NCStrategy.SNAKE_TO_CAMEL_UP_FIRST;

    /**
     * @param name name
     * @return java.lang.String
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-11-22 01:25
     */
    @Override
    public String convert(String name) {
        if (StringUtils.isBlank(name)) {
            return StringConst.EMPTY;
        }

        return snakeToCamel(name).toString();

    }

    /**
     * @param name name
     * @return java.lang.StringBuilder
     * @title snakeToCamel
     * @description
     * @author BiJi'an
     * @date 2022-11-22 01:26
     */
    protected StringBuilder snakeToCamel(String name) {
        StringBuilder result = new StringBuilder();
        String[] camels = name.split(StringConst.UNDERSCORE);
        Arrays.stream(camels).filter(camel -> !StringUtils.isBlank(camel)).forEach(camel -> result
                .append(Character.toUpperCase(camel.charAt(0))).append(camel.substring(1).toLowerCase()));
        return result;
    }

}

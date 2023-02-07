package io.github.kylinhunter.commons.name;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.strings.CharConst;
import io.github.kylinhunter.commons.strings.StringConst;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
@C
@Data
public class SnakeToCamel {

    /**
     * @param name name
     * @return java.lang.String
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-11-22 01:25
     */
    public String convert(String name) {
        return this.convert(name, CamelFormat.LOWER);
    }

    /**
     * @param name        name
     * @param camelFormat camelFormat
     * @return java.lang.String
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2023-02-08 00:25
     */
    public String convert(String name, CamelFormat camelFormat) {
        if (StringUtils.isBlank(name)) {
            return StringConst.EMPTY;
        }
        if (!SnakeToCamel.isSnake(name)) {
            return name;
        }

        StringBuilder result = new StringBuilder();

        char sep = name.indexOf(StringConst.UNDERSCORE) > 0 ? CharConst.UNDERSCORE : CharConst.HYPHEN;
        String[] camels = name.split(sep + "");
        Arrays.stream(camels).filter(camel -> !StringUtils.isBlank(camel)).forEach(camel ->
                result.append(Character.toUpperCase(camel.charAt(0))).append(camel.substring(1).toLowerCase())
        );
        if (camelFormat == CamelFormat.LOWER) {
            result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
        }
        return result.toString();
    }

    /**
     * @param name name
     * @return boolean
     * @title isSnake
     * @description
     * @author BiJi'an
     * @date 2023-02-08 00:44
     */

    public static boolean isSnake(String name) {
        return name.indexOf(CharConst.UNDERSCORE) > 0 || name.indexOf(CharConst.HYPHEN) > 0;

    }
}

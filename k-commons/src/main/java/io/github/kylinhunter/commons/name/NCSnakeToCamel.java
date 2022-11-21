package io.github.kylinhunter.commons.name;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.strings.StringConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
@EqualsAndHashCode(callSuper = true)
@C
@Data
public class NCSnakeToCamel extends NCSnakeToCamelUpperFirst implements NameConvertor {
    private NCStrategy ncStrategy = NCStrategy.SNAKE_TO_CAMEL;

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

        StringBuilder stringBuilder = snakeToCamel(name);
        stringBuilder.setCharAt(0, Character.toLowerCase(stringBuilder.charAt(0)));
        return stringBuilder.toString();
    }
}

package io.github.kylinhunter.commons.util.name;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.util.StringConst;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 **/
@C
@Data
public class NCSnakeToCamel extends NCSnakeToCamelUpperFirst {
    private NCStrategy ncStrategy = NCStrategy.SNAKE_TO_CAMEL;

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

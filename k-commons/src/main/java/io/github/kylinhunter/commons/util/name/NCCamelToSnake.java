package io.github.kylinhunter.commons.util.name;

import org.apache.commons.lang3.StringUtils;

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
public class NCCamelToSnake implements NamingConvertor {
    private NCStrategy ncStrategy = NCStrategy.CAMEL_TO_SNAKE;


    public String convert(String name) {

        if (StringUtils.isBlank(name)) {
            return StringUtils.EMPTY;
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

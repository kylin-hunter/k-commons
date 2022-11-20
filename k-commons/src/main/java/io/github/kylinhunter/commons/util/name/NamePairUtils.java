package io.github.kylinhunter.commons.util.name;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.exception.inner.ParamException;
import io.github.kylinhunter.commons.util.StringPool;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-21 19:55
 **/
public class NamePairUtils {

    public static NamePair toNamePair(String str) {
        if (!StringUtils.isEmpty(str)) {
            NamePair namePair = new NamePair();
            if (str.indexOf(StringPool.UNDERSCORE) > 0) {
                namePair.setSnake(str);
                namePair.setCamel(NamingConvertors.convert(NCStrategy.SNAKE_TO_CAMEL, str));
            } else {
                namePair.setCamel(str);
                namePair.setSnake(NamingConvertors.convert(NCStrategy.CAMEL_TO_SNAKE, str));
            }

            return namePair;
        }
        throw new ParamException("str is emtpty");
    }

}

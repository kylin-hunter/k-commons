package com.kylinhunter.plat.commons.util.name;

import com.kylinhunter.plat.commons.exception.inner.ParamException;

import jodd.util.StringPool;
import jodd.util.StringUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-21 19:55
 **/
public class NamePairUtils {

    public static NamePair toNamePair(String str) {
        if (!StringUtil.isEmpty(str)) {
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

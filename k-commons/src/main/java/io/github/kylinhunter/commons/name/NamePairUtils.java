package io.github.kylinhunter.commons.name;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.exception.inner.ParamException;
import io.github.kylinhunter.commons.strings.StringConst;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-21 19:55
 **/
public class NamePairUtils {
    private static final NameConvertors NAME_CONVERTORS = CF.get(NameConvertors.class);

    /**
     * @param str str
     * @return io.github.kylinhunter.commons.name.NamePair
     * @title toNamePair
     * @description
     * @author BiJi'an
     * @date 2022-11-22 01:23
     */
    public static NamePair toNamePair(String str) {
        if (!StringUtils.isEmpty(str)) {
            NamePair namePair = new NamePair();
            if (str.indexOf(StringConst.UNDERSCORE) > 0) {
                namePair.setSnake(str);
                namePair.setCamel(NAME_CONVERTORS.convert(NCStrategy.SNAKE_TO_CAMEL, str));
            } else {
                namePair.setCamel(str);
                namePair.setSnake(NAME_CONVERTORS.convert(NCStrategy.CAMEL_TO_SNAKE, str));
            }

            return namePair;
        }
        throw new ParamException("str is emtpty");
    }

}

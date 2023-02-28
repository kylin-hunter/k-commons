package io.github.kylinhunter.commons.name;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.exception.embed.ParamException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-21 19:55
 **/
public class NamePairUtils {

    /**
     * @param str str
     * @return io.github.kylinhunter.commons.name.NamePair
     * @title toNamePair
     * @description
     * @author BiJi'an
     * @date 2023-02-08 01:11
     */
    public static NamePair toNamePair(String str) {
        return toNamePair(str, CamelFormat.LOWER, SnakeFormat.LOWWER_UNDERSCORE);
    }

    /**
     * @param str         str
     * @param camelFormat camelFormat
     * @return io.github.kylinhunter.commons.name.NamePair
     * @title toNamePair
     * @description
     * @author BiJi'an
     * @date 2023-02-08 01:11
     */
    public static NamePair toNamePair(String str, CamelFormat camelFormat) {
        return toNamePair(str, camelFormat, SnakeFormat.LOWWER_UNDERSCORE);
    }

    /**
     * @param str         str
     * @param snakeFormat snakeFormat
     * @return io.github.kylinhunter.commons.name.NamePair
     * @title toNamePair
     * @description
     * @author BiJi'an
     * @date 2023-02-08 01:11
     */
    public static NamePair toNamePair(String str, SnakeFormat snakeFormat) {
        return toNamePair(str, CamelFormat.LOWER, snakeFormat);
    }

    /**
     * @param str str
     * @return io.github.kylinhunter.commons.name.NamePair
     * @title toNamePair
     * @description
     * @author BiJi'an
     * @date 2022-11-22 01:23
     */
    public static NamePair toNamePair(String str, CamelFormat camelFormat, SnakeFormat snakeFormat) {

        if (!StringUtils.isEmpty(str)) {
            NamePair namePair = new NamePair();
            if (SnakeToCamelUtils.isSnake(str)) {
                namePair.setSnake(str);
                namePair.setCamel(SnakeToCamelUtils.convert(str, camelFormat));
            } else {
                namePair.setCamel(str);
                namePair.setSnake(CamelToSnakeUtils.convert(str, snakeFormat));
            }

            return namePair;
        }
        throw new ParamException("str is emtpty");
    }

}

package io.github.kylinhunter.commons.jdbc.meta.parser.imp;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.meta.parser.ColumnParser;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 **/
@C
public class ColumnParserSqlServer extends ColumnParserMysql {
    /**
     * @see ColumnParser#calJavaClass(int)
     */
    public Class<?> calJavaClass(int dataType) {
        return super.calJavaClass(dataType);
    }

}
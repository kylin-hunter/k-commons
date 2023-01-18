package io.github.kylinhunter.commons.jdbc.constant;

import io.github.kylinhunter.commons.component.CT;
import io.github.kylinhunter.commons.jdbc.meta.parser.ColumnParser;
import io.github.kylinhunter.commons.jdbc.meta.parser.imp.ColumnParserMysql;
import io.github.kylinhunter.commons.jdbc.meta.parser.imp.ColumnParserOracle;
import io.github.kylinhunter.commons.jdbc.meta.parser.imp.ColumnParserSqlServer;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023/1/18
 **/
public enum ColumnParserType implements CT<ColumnParser> {
    MYSQL(ColumnParserMysql.class), ORACLE(ColumnParserOracle.class), SQL_SERVER(ColumnParserSqlServer.class);

    @Getter
    private final Class<? extends ColumnParser> clazz;

    ColumnParserType(Class<? extends ColumnParser> clazz) {
        this.clazz = clazz;
    }

}

package io.github.kylinhunter.commons.jdbc.datasource;

import javax.sql.DataSource;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 00:34
 **/
public class DataSourceUtils {
    @Getter
    private static final DataSource defaultDataSource;

    static {
        defaultDataSource = new HikariDataSourceEx();
    }
}

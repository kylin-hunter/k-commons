package io.github.kylinhunter.commons.generator.datasource;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 16:12
 **/
public class DataSourceHelper {
    static DataSource defaultDataSource;

    static {
        init();
    }

    static void init() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://10.233.28.42:8306/cskb_1_0_work?useUnicode=true&characterEncoding=utf8&useSSL"
                + "=false&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
        config.setUsername("root");
        config.setPassword("123456a?");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMinimumIdle(1);
        config.setMaximumPoolSize(10);
        defaultDataSource = new HikariDataSource(config);
    }
}

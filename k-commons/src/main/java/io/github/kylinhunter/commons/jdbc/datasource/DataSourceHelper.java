package io.github.kylinhunter.commons.jdbc.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.kylinhunter.commons.yaml.YamlHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 16:12
 **/
public class DataSourceHelper {
    static DataSource defaultDataSource;
    static String DEFAULT_PATH = "kylinhunter/db-config.yaml";

    static {
        init();
    }

    static void init() {
        init(null);
    }

    static void init(String path) {
        path = StringUtils.defaultString(path, DEFAULT_PATH);
        DBConfig dbConfig = YamlHelper.loadFromClassPath(DBConfig.class, path);
        HikariConfig hikariConfig = buildConfig(dbConfig);
        defaultDataSource = new HikariDataSource(hikariConfig);
    }

    private static HikariConfig buildConfig(DBConfig dbConfig) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dbConfig.getDriverClassName());
        hikariConfig.setJdbcUrl(dbConfig.getJdbcUrl());
        hikariConfig.setUsername(dbConfig.getUsername());
        hikariConfig.setPassword(dbConfig.getPassword());
        int idleTimeout = dbConfig.getIdleTimeout();
        if (idleTimeout > 0) {
            hikariConfig.setIdleTimeout(idleTimeout);
        }
        int minimumIdle = dbConfig.getMinimumIdle();
        if (minimumIdle > 0) {
            hikariConfig.setMinimumIdle(minimumIdle);
        }

        int maximumPoolSize = dbConfig.getMaximumPoolSize();
        if (maximumPoolSize > 0) {
            hikariConfig.setMaximumPoolSize(maximumPoolSize);

        }
        int connectionTimeout = dbConfig.getConnectionTimeout();
        if (connectionTimeout > 0) {
            hikariConfig.setConnectionTimeout(connectionTimeout);

        }
        Map<String, String> dataSourceProperties = dbConfig.getDataSourceProperties();
        if (!MapUtils.isEmpty(dataSourceProperties)) {
            dataSourceProperties.forEach((k, v) -> {
                hikariConfig.addDataSourceProperty(k, v);
            });
        }
        return hikariConfig;
    }
}

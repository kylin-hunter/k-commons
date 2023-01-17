package io.github.kylinhunter.commons.jdbc.datasource;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.kylinhunter.commons.yaml.YamlHelper;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 16:12
 **/
@Getter
public class HikariDataSourceEx extends HikariDataSource {
    static String DEFAULT_PATH = "k-db-config.yaml";

    public HikariDataSourceEx() {
        this(null);
    }

    public HikariDataSourceEx(String path) {
        super(buildConfig(path));

    }

    private static HikariConfig buildConfig(String path) {
        path = StringUtils.defaultString(path, DEFAULT_PATH);
        DBConfig dbConfig = YamlHelper.loadFromClassPath(DBConfig.class, path);
        return buildConfig(dbConfig);
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
            dataSourceProperties.forEach(hikariConfig::addDataSourceProperty);
        }
        return hikariConfig;
    }

}

package io.github.kylinhunter.commons.jdbc.config.hikari;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.jdbc.config.datasource.DSInfo;
import io.github.kylinhunter.commons.jdbc.config.datasource.DataSourceConfig;
import io.github.kylinhunter.commons.jdbc.config.datasource.PoolInfo;
import java.util.List;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-25 23:25
 */
public class ExHikariConfigHelper {

  /**
   * @param dataSourceConfig dataSourceConfig
   * @return com.zaxxer.hikari.HikariConfig
   * @title buildConfig
   * @description
   * @author BiJi'an
   * @date 2023-01-17 22:27
   */
  public static List<ExHikariConfig> parse(DataSourceConfig dataSourceConfig) {

    List<DSInfo> dsInfos = dataSourceConfig.getDatasources();

    List<ExHikariConfig> exHikariConfigs = ListUtils.newArrayList();

    for (int i = 0; i < dsInfos.size(); i++) {

      DSInfo datasourceInfo = dsInfos.get(i);
      PoolInfo poolInfo = datasourceInfo.getPool();
      Map<String, String> dataSourceProperties = datasourceInfo.getProperties();

      ExHikariConfig exHikariConfig = new ExHikariConfig(i,
          datasourceInfo.getName());
      exHikariConfig.setDriverClassName(datasourceInfo.getDriverClassName());
      exHikariConfig.setJdbcUrl(datasourceInfo.getJdbcUrl());
      exHikariConfig.setUsername(datasourceInfo.getUsername());
      exHikariConfig.setPassword(datasourceInfo.getPassword());

      int idleTimeout = poolInfo.getIdleTimeout();
      if (idleTimeout > 0) {
        exHikariConfig.setIdleTimeout(idleTimeout);
      }

      int minimumIdle = poolInfo.getMinimumIdle();
      if (minimumIdle > 0) {
        exHikariConfig.setMinimumIdle(minimumIdle);
      }

      int maximumPoolSize = poolInfo.getMaximumPoolSize();
      if (maximumPoolSize > 0) {
        exHikariConfig.setMaximumPoolSize(maximumPoolSize);
      }
      int connectionTimeout = poolInfo.getConnectionTimeout();
      if (connectionTimeout > 0) {
        exHikariConfig.setConnectionTimeout(connectionTimeout);
      }

      if (!MapUtils.isEmpty(dataSourceProperties)) {
        dataSourceProperties.forEach(exHikariConfig::addDataSourceProperty);
      }

      if (datasourceInfo.isPrimary()) {
        exHikariConfigs.add(0, exHikariConfig);
      } else {
        exHikariConfigs.add(exHikariConfig);
      }
    }

    return exHikariConfigs;
  }
}
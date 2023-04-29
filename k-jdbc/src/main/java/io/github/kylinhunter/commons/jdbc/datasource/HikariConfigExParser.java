package io.github.kylinhunter.commons.jdbc.datasource;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.jdbc.datasource.bean.DataSourceConfig;
import io.github.kylinhunter.commons.jdbc.datasource.bean.DataSourceInfo;
import io.github.kylinhunter.commons.jdbc.datasource.bean.HikariConfigEx;
import io.github.kylinhunter.commons.jdbc.datasource.bean.PoolInfo;
import io.github.kylinhunter.commons.name.NameRule;
import io.github.kylinhunter.commons.yaml.YamlHelper;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 16:12
 */
@Getter
@Slf4j
public class HikariConfigExParser {
  private static final String DEFAULT_PATH = "k-db-config.yaml";

  /**
   * @param path path
   * @return com.zaxxer.hikari.HikariConfig
   * @title buildConfig
   * @description
   * @author BiJi'an
   * @date 2023-01-17 22:27
   */
  public List<HikariConfigEx> load(String path) {
    path = StringUtils.defaultString(path, DEFAULT_PATH);
    DataSourceConfig DataSourceConfig =
        YamlHelper.loadFromPath(DataSourceConfig.class, path, NameRule.CAMEL_LOW);
    List<DataSourceInfo> dataSourceInfos = DataSourceConfig.getDatasources();

    List<HikariConfigEx> hikariConfigExs = ListUtils.newArrayList();

    for (int i = 0; i < dataSourceInfos.size(); i++) {

      DataSourceInfo datasourceInfo = dataSourceInfos.get(i);
      PoolInfo poolInfo = datasourceInfo.getPool();
      Map<String, String> dataSourceProperties = datasourceInfo.getDataSourceProperties();

      HikariConfigEx hikariConfigEx = new HikariConfigEx(i, datasourceInfo.getName());
      hikariConfigEx.setDriverClassName(datasourceInfo.getDriverClassName());
      hikariConfigEx.setJdbcUrl(datasourceInfo.getJdbcUrl());
      hikariConfigEx.setUsername(datasourceInfo.getUsername());
      hikariConfigEx.setPassword(datasourceInfo.getPassword());

      int idleTimeout = poolInfo.getIdleTimeout();
      if (idleTimeout > 0) {
        hikariConfigEx.setIdleTimeout(idleTimeout);
      }

      int minimumIdle = poolInfo.getMinimumIdle();
      if (minimumIdle > 0) {
        hikariConfigEx.setMinimumIdle(minimumIdle);
      }

      int maximumPoolSize = poolInfo.getMaximumPoolSize();
      if (maximumPoolSize > 0) {
        hikariConfigEx.setMaximumPoolSize(maximumPoolSize);
      }
      int connectionTimeout = poolInfo.getConnectionTimeout();
      if (connectionTimeout > 0) {
        hikariConfigEx.setConnectionTimeout(connectionTimeout);
      }

      if (!MapUtils.isEmpty(dataSourceProperties)) {
        dataSourceProperties.forEach(hikariConfigEx::addDataSourceProperty);
      }

      if (datasourceInfo.isPrimary()) {
        hikariConfigExs.add(0, hikariConfigEx);
      } else {
        hikariConfigExs.add(hikariConfigEx);
      }
    }

    return hikariConfigExs;
  }
}

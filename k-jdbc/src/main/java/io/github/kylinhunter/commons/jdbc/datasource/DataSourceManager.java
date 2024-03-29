/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.jdbc.datasource;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.check.ThrowChecker;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.jdbc.config.DSConfigLoader;
import io.github.kylinhunter.commons.jdbc.config.datasource.hikari.ExHikariConfig;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 00:34
 */
@Slf4j
@NoArgsConstructor
public class DataSourceManager {

  private DataSource dataSource;
  private final Map<Object, ExDataSource> allDataSources = MapUtils.newHashMap();

  private SqlExecutor sqlExecutor;

  private final Map<Object, SqlExecutor> allSqlExecutors = MapUtils.newHashMap();

  private volatile boolean initialized = false;

  private String dbConfigPath;
  private final DSConfigLoader dsConfigLoader = new DSConfigLoader();

  public DataSourceManager(boolean dbConfigEnabled) {
    if (dbConfigEnabled) {
      init(DSConfigLoader.DEFAULT_PATH);
    }
  }

  public DataSourceManager(boolean dbConfigEnabled, String dbConfigPath) {
    if (dbConfigEnabled) {
      init(dbConfigPath);
    }
  }

  public void init() {
    dbConfigPath = StringUtil.defaultString(dbConfigPath, DSConfigLoader.DEFAULT_PATH);
    init(dbConfigPath);
  }

  /**
   * @param path path
   * @title initFromPath
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public void init(String path) {
    try {
      List<ExHikariConfig> exHikariConfigs = dsConfigLoader.load(path);
      init(exHikariConfigs);
    } catch (Exception e) {
      throw new InitException("init error", e);
    }
  }

  /**
   * @param exHikariConfigs exHikariConfigs
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-03 00:57
   */
  public synchronized void init(List<ExHikariConfig> exHikariConfigs) {

    if (!initialized) {
      close();
      List<ExDataSource> allExDataSources = Lists.newArrayList();
      if (CollectionUtils.isEmpty(exHikariConfigs)) {
        throw new InitException(" can't find datasource config");
      }
      for (ExHikariConfig exHikariConfig : exHikariConfigs) {
        Class<? extends ExDataSource> clazz = DSCreator.create(HikariDataSource.class);

        ExDataSource exDataSource =
            ObjectCreator.create(
                clazz, new Class[] {HikariConfig.class}, new Object[] {exHikariConfig});
        exDataSource.setDsNo(exHikariConfig.getNo());
        exDataSource.setDsName(exHikariConfig.getName());
        allExDataSources.add(exDataSource);
      }

      if (allExDataSources.size() > 0) {
        ExDataSource firstDatasource = allExDataSources.get(0);
        this.dataSource = firstDatasource;
        this.sqlExecutor = new SqlExecutor(firstDatasource);
        allExDataSources.forEach(this::addDatasource);
      }
      initialized = true;
    }
  }

  /**
   * @param exDataSource exDataSource
   * @title addDatasource
   * @description addDatasource
   * @author BiJi'an
   * @date 2023-12-10 00:20
   */
  private void addDatasource(ExDataSource exDataSource) {
    allDataSources.put(exDataSource.getDsNo(), exDataSource);
    allDataSources.put(exDataSource.getDsName(), exDataSource);
    SqlExecutor sqlExecutor = new SqlExecutor(exDataSource);
    allSqlExecutors.put(exDataSource.getDsNo(), sqlExecutor);
    allSqlExecutors.put(exDataSource.getDsName(), sqlExecutor);
  }

  /**
   * @param dsKey dsKey
   * @return io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx
   * @title getByNo
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  private ExDataSource get(Object dsKey) {
    ThrowChecker.checkNotNull(dsKey, "dsKey can't be null");
    ExDataSource exDataSource = allDataSources.get(dsKey);
    if (exDataSource == null) {
      throw new JdbcException("no  Datasource for:" + dsKey);
    }
    return exDataSource;
  }

  /**
   * @return javax.sql.DataSource
   * @title getDefaultDataSource
   * @description getDefaultDataSource
   * @author BiJi'an
   * @date 2023-12-03 16:45
   */
  public DataSource get() {
    if (dataSource == null) {
      throw new JdbcException("no default Datasource ");
    }
    return dataSource;
  }

  /**
   * @param no no
   * @return io.github.kylinhunter.commons.jdbc.datasource.ExDataSource
   * @title getByNo
   * @description getByNo
   * @author BiJi'an
   * @date 2023-12-03 15:32
   */
  public ExDataSource getByNo(int no) {
    return this.get(no);
  }

  /**
   * @param name name
   * @return io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx
   * @title getByName
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public ExDataSource getByName(String name) {
    return this.get(name);
  }

  /**
   * @param dsKey dsKey
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getSqlExecutor
   * @description getSqlExecutor
   * @author BiJi'an
   * @date 2023-12-04 23:17
   */
  private SqlExecutor getSqlExecutor(Object dsKey) {
    SqlExecutor sqlExecutor = allSqlExecutors.get(dsKey);
    if (sqlExecutor == null) {
      throw new JdbcException("no  SqlExecutor for:" + dsKey);
    }
    return sqlExecutor;
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getDefaultSqlExecutor
   * @description getDefaultSqlExecutor
   * @author BiJi'an
   * @date 2023-12-03 16:53
   */
  public SqlExecutor getSqlExecutor() {

    if (sqlExecutor == null) {
      throw new JdbcException("no default SqlExecutor ");
    }
    return sqlExecutor;
  }

  /**
   * @param no no
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getSqlExecutorByNo
   * @description getSqlExecutorByNo
   * @author BiJi'an
   * @date 2023-12-04 23:16
   */
  public SqlExecutor getSqlExecutorByNo(int no) {
    return this.getSqlExecutor(no);
  }

  /**
   * @param name name
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getSqlExecutorByName
   * @description getSqlExecutorByName
   * @author BiJi'an
   * @date 2023-12-04 23:16
   */
  public SqlExecutor getSqlExecutorByName(String name) {
    return this.getSqlExecutor(name);
  }

  /**
   * @title close
   * @description close
   * @author BiJi'an
   * @date 2023-12-03 16:54
   */
  public void close() {
    for (ExDataSource exDataSource : allDataSources.values()) {
      IOUtil.closeQuietly(exDataSource);
    }
    allDataSources.clear();
  }

  /**
   * @param jdbcUrl jdbcUrl
   * @param username username
   * @param password password
   * @return javax.sql.DataSource
   * @title createDataSource
   * @description createDataSource
   * @author BiJi'an
   * @date 2023-12-10 00:02
   */
  public static DataSource createDataSource(String jdbcUrl, String username, String password) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(jdbcUrl);
    hikariConfig.setUsername(username);
    hikariConfig.setPassword(password);
    return new HikariDataSource(hikariConfig);
  }

  /**
   * @param jdbcUrl jdbcUrl
   * @param username username
   * @param password password
   * @return javax.sql.DataSource
   * @title createDataSource
   * @description createDataSource
   * @author BiJi'an
   * @date 2023-12-10 00:44
   */
  public static DataSource createDataSource(JdbcUrl jdbcUrl, String username, String password) {
    return createDataSource(JdbcUtils.toString(jdbcUrl), username, password);
  }
}

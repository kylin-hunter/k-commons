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
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.jdbc.config.ConfigLoader;
import io.github.kylinhunter.commons.jdbc.config.datasource.hikari.ExHikariConfig;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 00:34
 */
@Slf4j
public class DataSourceManager implements AutoCloseable {

  private DataSource defaultDataSource;
  private final Map<Object, ExDataSource> allDataSources = MapUtils.newHashMap();

  private SqlExecutor defaultSqlExecutor;

  private volatile boolean initialized = false;

  public void init() {
    init(ConfigLoader.DEFAULT_PATH);
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
      ConfigLoader configLoader = new ConfigLoader();
      List<ExHikariConfig> exHikariConfigs = configLoader.load(path);
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
        this.defaultDataSource = firstDatasource;
        this.defaultSqlExecutor = new SqlExecutor(firstDatasource);
        allExDataSources.forEach(
            exDataSource -> {
              allDataSources.put(exDataSource.getDsNo(), exDataSource);
              allDataSources.put(exDataSource.getDsName(), exDataSource);
            });
      }
      initialized = true;
    }
  }

  /**
   * @return javax.sql.DataSource
   * @title getDefaultDataSource
   * @description getDefaultDataSource
   * @author BiJi'an
   * @date 2023-12-03 16:45
   */
  public DataSource getDefaultDataSource() {
    if (defaultDataSource == null) {
      init();
    }
    if (defaultDataSource == null) {
      throw new JdbcException("no default Datasource ");
    }
    return defaultDataSource;
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getDefaultSqlExecutor
   * @description getDefaultSqlExecutor
   * @author BiJi'an
   * @date 2023-12-03 16:53
   */
  public SqlExecutor getDefaultSqlExecutor() {

    if (defaultSqlExecutor == null) {
      init();
    }
    if (defaultSqlExecutor == null) {
      throw new JdbcException("no default SqlExecutor ");
    }
    return defaultSqlExecutor;
  }

  /**
   * @param dsKey dsKey
   * @return io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx
   * @title getByNo
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  private ExDataSource getDatasource(Object dsKey) {
    ExDataSource exDataSource = allDataSources.get(dsKey);
    if (exDataSource == null) {
      init();
    }
    exDataSource = allDataSources.get(dsKey);
    if (exDataSource == null) {
      throw new JdbcException("dsKey  Datasource for:" + dsKey);
    }
    return exDataSource;
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
    return this.getDatasource(no);
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
    return this.getDatasource(name);
  }

  /**
   * @title close
   * @description close
   * @author BiJi'an
   * @date 2023-12-03 16:54
   */
  @Override
  public void close() {
    for (ExDataSource exDataSource : allDataSources.values()) {
      IOUtil.closeQuietly(exDataSource);
    }
    allDataSources.clear();
  }
}

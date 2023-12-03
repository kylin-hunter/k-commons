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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.jdbc.config.ConfigLoader;
import io.github.kylinhunter.commons.jdbc.config.datasource.hikari.ExHikariConfig;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 00:34
 */
@Slf4j
public class DataSourceManager {

  @Getter
  private ExDataSource defaultDataSource;
  private final Map<Object, ExDataSource> DATA_SOURCES = MapUtils.newHashMap();

  public DataSourceManager() {
    init(ConfigLoader.DEFAULT_PATH);
  }

  /**
   * @param path path
   * @title initFromPath
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public synchronized void init(String path) {
    try {
      ConfigLoader configLoader = new ConfigLoader();
      List<ExHikariConfig> exHikariConfigs = configLoader.load(path);
      init(exHikariConfigs);
    } catch (Exception e) {
      log.error("init error", e);
    }

  }

  /**
   * @param exHikariConfigs exHikariConfigs
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-04 00:57
   */

  public synchronized void init(List<ExHikariConfig> exHikariConfigs) {
    closeAll();
    if (CollectionUtils.isEmpty(exHikariConfigs)) {
      throw new InitException(" can't find datasource config");
    }
    for (int i = 0; i < exHikariConfigs.size(); i++) {
      ExHikariConfig exHikariConfig = exHikariConfigs.get(i);

      int no = exHikariConfig.getNo();
      String name = exHikariConfig.getName();
      Class<? extends ExDataSource> clazz = DSCreator.create(HikariDataSource.class);

      ExDataSource exDataSource =
          ObjectCreator.create(
              clazz, new Class[]{HikariConfig.class}, new Object[]{exHikariConfig});
      if (i == 0) {
        defaultDataSource = exDataSource;
      }
      DATA_SOURCES.put(no, exDataSource);
      DATA_SOURCES.put(name, exDataSource);

    }
  }

  /**
   * @title closeAll
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  private void closeAll() {
    for (ExDataSource exDataSource : DATA_SOURCES.values()) {
      IOUtil.closeQuietly(exDataSource);
    }
  }

  /**
   * @param no no
   * @return io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx
   * @title getByNo
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public ExDataSource getByNo(int no) {
    return DATA_SOURCES.get(no);
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
    return DATA_SOURCES.get(name);
  }
}

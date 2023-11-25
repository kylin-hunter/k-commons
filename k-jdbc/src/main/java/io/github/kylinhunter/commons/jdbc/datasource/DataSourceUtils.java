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
import io.github.kylinhunter.commons.jdbc.config.hikari.ExHikariConfig;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 00:34
 */
public class DataSourceUtils {

  private static final ConfigLoader EX_HIKARI_CONFIG_PARSER = new ConfigLoader();
  @Getter
  private static DataSourceEx defaultDataSource;
  private static final Map<Integer, DataSourceEx> ID_DATA_SOURCES = MapUtils.newHashMap();
  private static final Map<String, DataSourceEx> NAME_DATA_SOURCES = MapUtils.newHashMap();

  static {
    init(null);
  }

  /**
   * @param path path
   * @return void
   * @title load
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public static synchronized void init(String path) {
    closeAll();
    List<ExHikariConfig> exHikariConfigs = EX_HIKARI_CONFIG_PARSER.load(path);
    if (CollectionUtils.isEmpty(exHikariConfigs)) {
      throw new InitException(" can't find datasource config");
    }
    for (ExHikariConfig exHikariConfig : exHikariConfigs) {
      int no = exHikariConfig.getNo();
      String name = exHikariConfig.getName();
      Class<? extends DataSourceEx> clazz = DSCreator.create(HikariDataSource.class);

      DataSourceEx dataSourceEx =
          ObjectCreator.create(
              clazz, new Class[]{HikariConfig.class}, new Object[]{exHikariConfig});
      if (defaultDataSource == null) {
        defaultDataSource = dataSourceEx;
      }
      ID_DATA_SOURCES.put(no, dataSourceEx);
      NAME_DATA_SOURCES.put(name, dataSourceEx);
    }
  }

  /**
   * @return void
   * @title closeAll
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  private static void closeAll() {
    for (DataSourceEx dataSourceEx : ID_DATA_SOURCES.values()) {
      IOUtil.closeQuietly(dataSourceEx);
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
  public static DataSourceEx getByNo(int no) {
    return ID_DATA_SOURCES.get(no);
  }

  /**
   * @param name name
   * @return io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx
   * @title getByName
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public static DataSourceEx getByName(String name) {
    return NAME_DATA_SOURCES.get(name);
  }
}

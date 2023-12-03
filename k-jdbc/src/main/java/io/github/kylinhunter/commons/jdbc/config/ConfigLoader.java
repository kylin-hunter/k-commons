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
package io.github.kylinhunter.commons.jdbc.config;

import io.github.kylinhunter.commons.jdbc.config.datasource.DataSourceConfig;
import io.github.kylinhunter.commons.jdbc.config.datasource.hikari.ExHikariConfig;
import io.github.kylinhunter.commons.jdbc.config.datasource.hikari.ExHikariConfigHelper;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.name.NameRule;
import io.github.kylinhunter.commons.utils.yaml.YamlHelper;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 16:12
 */
@Getter
@Slf4j
public class ConfigLoader {

  public static final String DEFAULT_PATH = "k-db-config.yaml";

  /**
   * @param path path
   * @return io.github.kylinhunter.commons.jdbc.config.datasource.DataSourceConfig
   * @title load
   * @description load
   * @author BiJi'an
   * @date 2023-11-25 22:58
   */
  public List<ExHikariConfig> load(String path) {
    path = StringUtil.defaultString(path, DEFAULT_PATH);
    DataSourceConfig dataSourceConfig =
        YamlHelper.loadFromPath(DataSourceConfig.class, path, NameRule.CAMEL_LOW);
    return ExHikariConfigHelper.parse(dataSourceConfig);
  }

  /**
   * @return
   *     java.util.List<io.github.kylinhunter.commons.jdbc.config.datasource.hikari.ExHikariConfig>
   * @title load
   * @description load
   * @author BiJi'an
   * @date 2023-12-04 00:44
   */
  public List<ExHikariConfig> load() {
    return load(DEFAULT_PATH);
  }
}

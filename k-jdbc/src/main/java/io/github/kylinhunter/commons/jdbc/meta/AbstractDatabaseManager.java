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
package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-29 22:34
 */
public class AbstractDatabaseManager {

  protected DataSource defaultDataSource;

  protected QueryRunner defaultQueryRunner;

  public AbstractDatabaseManager() {
    this.defaultDataSource = DataSourceUtils.getDefaultDataSource();
    this.defaultQueryRunner = new QueryRunner(this.defaultDataSource);
  }

  public AbstractDatabaseManager(DataSource defaultDataSource) {
    this.defaultDataSource = defaultDataSource;
    this.defaultQueryRunner = new QueryRunner(this.defaultDataSource);
  }
}

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
package io.github.kylinhunter.commons.jdbc.meta.column.imp;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CM;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Slf4j
@C
public class SqlServerColumnReader extends MysqlColumnReader {

  @CM
  public SqlServerColumnReader() {
    this(null);
  }

  public SqlServerColumnReader(DataSource dataSource) {
    super(dataSource);
    this.dbType = DbType.SQL_SERVER;
    this.columnParser = new SqlServerColumnParser();
  }
}
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
package io.github.kylinhunter.commons.jdbc.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023/1/18
 */
@RequiredArgsConstructor
public enum DbType {
  MYSQL(ColumnParserType.MYSQL, JdbcUrlParserType.MYSQL, "Mysql"),
  ORACLE(ColumnParserType.ORACLE, JdbcUrlParserType.ORACLE, "Oracle"),
  SQL_SERVER(ColumnParserType.SQL_SERVER, JdbcUrlParserType.SQL_SERVER, "SqlServer");

  @Getter
  private final ColumnParserType columnParserType;

  @Getter
  private final JdbcUrlParserType jdbcUrlParserType;
  @Getter
  private final String prefix;
}

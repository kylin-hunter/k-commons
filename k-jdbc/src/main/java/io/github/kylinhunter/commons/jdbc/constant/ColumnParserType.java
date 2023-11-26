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

import io.github.kylinhunter.commons.component.CT;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnParser;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.MysqlColumnParser;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.OracleColumnParser;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.SqlServerColumnParser;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023/1/18
 */
public enum ColumnParserType implements CT<ColumnParser> {
  MYSQL(MysqlColumnParser.class),
  ORACLE(OracleColumnParser.class),
  SQL_SERVER(SqlServerColumnParser.class);

  @Getter
  private final Class<? extends ColumnParser> clazz;

  ColumnParserType(Class<? extends ColumnParser> clazz) {
    this.clazz = clazz;
  }
}

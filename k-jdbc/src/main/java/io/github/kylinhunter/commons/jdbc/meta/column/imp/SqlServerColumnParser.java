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

import io.github.kylinhunter.commons.jdbc.meta.column.ColumnParser;
import io.github.kylinhunter.commons.sys.KGenerated;

/**
 * @author BiJi'an
 * @description no-test ，Reserved functions: Support for sql-server
 * @date 2023-01-10 11:11
 */
@KGenerated
public class SqlServerColumnParser extends MysqlColumnParser {

  /**
   * @see ColumnParser#calJavaClass(int)
   */
  public Class<?> calJavaClass(int dataType) {
    // Reserved functions: Support for sql-server
    return super.calJavaClass(dataType);
  }
}

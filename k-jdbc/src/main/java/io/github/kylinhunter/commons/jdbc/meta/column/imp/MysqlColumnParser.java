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

import com.mysql.cj.MysqlType;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnParser;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.reflect.ClassUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */

public class MysqlColumnParser implements ColumnParser {

  /**
   * @see ColumnParser#calJavaClass(int)
   */
  public Class<?> calJavaClass(int dataType) {
    try {
      MysqlType mysqlType = MysqlType.getByJdbcType(dataType);
      String className = mysqlType.getClassName();
      if (!StringUtil.isEmpty(className)) {
        return ClassUtil.loadClass(className);
      }
    } catch (Exception e) {
      throw new InitException("can't get javaClass", e);
    }
    return null;
  }
}

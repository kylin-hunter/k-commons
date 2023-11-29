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
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.column.AbstractColumnReader;
import io.github.kylinhunter.commons.util.ObjectValues;
import java.sql.JDBCType;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Slf4j
@C
public class MysqlColumnReader extends AbstractColumnReader {

  @CM
  public MysqlColumnReader() {
    this.dbType = DbType.MYSQL;
    this.columnParser = new MysqlColumnParser();
  }

  public MysqlColumnReader(DataSource dataSource) {
    super(dataSource);
    this.columnParser = new MysqlColumnParser();
  }

  /**
   * @param columnMeta columnMeta
   * @param columName columName
   * @param value value
   * @return void
   * @title processMetadata
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:43
   */
  protected void processMetadata(ColumnMeta columnMeta, String columName, Object value) {
    //        log.info(columName + ":" + value);
    switch (columName) {
      case "TABLE_NAME":
        columnMeta.setTableName(ObjectValues.getString(value));
        break;
      case "COLUMN_NAME":
        columnMeta.setColumnName(ObjectValues.getString(value));
        break;

      case "DATA_TYPE":
        columnMeta.setDataType(ObjectValues.getInt(value, Integer.MIN_VALUE));
        try {
          JDBCType jdbcType = JDBCType.valueOf(columnMeta.getDataType());
          columnMeta.setJdbcType(jdbcType);
        } catch (Exception e) {
          log.error("jdbc type error", e);
        }
        break;
      case "TYPE_NAME":
        columnMeta.setTypeName(ObjectValues.getString(value));
        break;
      case "COLUMN_SIZE":
        columnMeta.setColumnSize(ObjectValues.getInt(value));
        break;
      case "DECIMAL_DIGITS":
        columnMeta.setDecimalDigits(ObjectValues.getInt(value));
        break;

      case "IS_AUTOINCREMENT":
        columnMeta.setAutoIncrement(ObjectValues.getBoolean(value));
        break;
      case "NULLABLE":
        columnMeta.setNullable(ObjectValues.getBoolean(value));
        break;
      case "REMARKS":
        columnMeta.setRemarks(ObjectValues.getString(value));
        break;
      default:
    }
  }
}

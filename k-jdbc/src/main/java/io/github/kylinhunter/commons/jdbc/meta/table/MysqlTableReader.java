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
package io.github.kylinhunter.commons.jdbc.meta.table;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.util.ObjectValues;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Slf4j
@C
public class MysqlTableReader extends AbstractTableReader {

  private static final DbType DATABASE_TYPE = DbType.MYSQL;

  /**
   * @param tableMeta tableMeta
   * @param columName columName
   * @param value     value
   * @return void
   * @title processMetadata
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:43
   */
  @Override
  protected void processMetadata(TableMeta tableMeta, String columName, Object value) {
    //        log.info(columName + ":" + value);
    switch (columName) {
      case "TABLE_NAME":
        tableMeta.setName(ObjectValues.getString(value));
        break;
      case "REMARKS":
        tableMeta.setRemarks(ObjectValues.getString(value));
        break;
      default:
    }
  }
}

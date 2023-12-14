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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex;

import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.TableMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.TableMonitorContext;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.WriteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:48
 */
public class MonitorWriteRowsEventDataProcessor extends WriteRowsEventDataProcessor {


  @Override
  protected void insertDataRecord(TableId tableId, WriteRowsEventData eventData, Context context) {
    TableMonitorContext tableMonitorContext = (TableMonitorContext) context;
    TableMonitorConfig tableMonitorConfig = tableMonitorContext.getTableMonitorConfig();
    String tableName = tableMonitorConfig.getTableName();
    if (!tableName.equals(tableId.getName())) {
      return;
    }

    ColumnMeta pkColumnMeta = getPkColumnMeta(tableMonitorContext);
    if (pkColumnMeta == null) {
      return;
    }
    List<Serializable[]> rows = eventData.getRows();
    for (Serializable[] row : rows) {
      processScanRecord(tableMonitorContext, row, pkColumnMeta);
    }
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta
   * @title getPkColumnMeta
   * @description getPkColumnMeta
   * @author BiJi'an
   * @date 2023-12-12 01:18
   */
  private ColumnMeta getPkColumnMeta(TableMonitorContext context) {
    TableMonitorConfig tableMonitorConfig = context.getTableMonitorConfig();
    String database = tableMonitorConfig.getDatabase();
    String tableName = tableMonitorConfig.getTableName();
    String tablePkName = tableMonitorConfig.getTablePkName();
    TableId tableId = new TableId(database, tableName);
    ColumnMetas columnMetas = databaseMetaCache.getColumnMetas(tableId);
    if (columnMetas != null) {
      return columnMetas.getByName(tablePkName);
    }
    return null;
  }

  /**
   * @param row          row
   * @param pkColumnMeta pkColumnMeta
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-12 01:42
   */
  private void processScanRecord(TableMonitorContext tableMonitorContext, Serializable[] row,
      ColumnMeta pkColumnMeta) {
    TableMonitorConfig tableMonitorConfig = tableMonitorContext.getTableMonitorConfig();
    TableMonitorTaskManager tableMonitorTaskManager = tableMonitorContext.getTableMonitorTaskManager();
    if (pkColumnMeta.getPos() < row.length) {
      tableMonitorTaskManager.saveOrUpdate(
          tableMonitorConfig.getDestination(),
          tableMonitorConfig.getTableName(),
          String.valueOf(row[pkColumnMeta.getPos()]),
          RowOP.INSERT);
    }
  }
}

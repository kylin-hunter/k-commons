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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor;

import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-23 02:26
 */
@RequiredArgsConstructor
public class MonitorManager {

  private final TableIdManager tableIdManager;

  /**
   * @param tableId tableId
   * @param monitorTables monitorTables
   * @param eventData eventData
   * @param callback callback
   * @title process
   * @description process
   * @author BiJi'an
   * @date 2023-12-23 02:25
   */
  public <T extends EventData> void process(
      long tableId, MonitorTables monitorTables, T eventData, EventDataCallback<T> callback) {
    TableMeta tableMeta = tableIdManager.getTableMeta(tableId);
    if (tableMeta != null) {
      for (MonitorTable monitorTable : monitorTables.getDatas()) {
        if (tableMeta.equals(monitorTable.getDatabase(), monitorTable.getName())) {
          ColumnMetas columnMetas = tableIdManager.getColumnMetas(tableId);
          if (columnMetas != null) {
            ColumnMeta columnMeta = columnMetas.getByName(monitorTable.getTablePkName());
            if (columnMeta != null) {
              callback.callback(monitorTable, eventData, columnMeta);
            }
          }
        }
      }
    }
  }
}

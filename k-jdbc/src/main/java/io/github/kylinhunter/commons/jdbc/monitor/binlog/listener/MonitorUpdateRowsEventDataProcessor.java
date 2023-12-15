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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.UpdateRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-16 00:48
 */
@RequiredArgsConstructor
public class MonitorUpdateRowsEventDataProcessor extends UpdateRowsEventDataProcessor {

  private final TableMonitorTaskManager tableMonitorTaskManager;
  private final MonitorTable monitorTable;

  @Override
  protected void updateDataRecord(UpdateRowsEventData eventData, Context context) {

    ColumnMeta pkColumnMeta =
        databaseMetaCache.getPkColumnMeta(
            eventData.getTableId(),
            monitorTable.getDatabase(),
            monitorTable.getName(),
            monitorTable.getTablePkName());
    if (pkColumnMeta != null) {
      List<Entry<Serializable[], Serializable[]>> rows = eventData.getRows();
      for (Entry<Serializable[], Serializable[]> row : rows) {
        processScanRecord(row.getValue(), pkColumnMeta);
      }
    }
  }

  /**
   * @param row row
   * @param pkColumnMeta pkColumnMeta
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-12 01:42
   */
  private void processScanRecord(Serializable[] row, ColumnMeta pkColumnMeta) {

    if (pkColumnMeta.getPos() < row.length) {
      tableMonitorTaskManager.saveOrUpdate(
          monitorTable.getDestination(),
          monitorTable.getName(),
          String.valueOf(row[pkColumnMeta.getPos()]),
          RowOP.UPDATE);
    }
  }
}

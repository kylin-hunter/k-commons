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

import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.UpdateRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
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
  private final MonitorTables monitorTables;

  private final MonitorManager monitorManager;

  @Override
  protected void updateDataRecord(UpdateRowsEventData eventData, Context context) {

    this.monitorManager.process(
        eventData.getTableId(), this.monitorTables, eventData, this::processScanRecord);
  }

  /**
   * @param monitorTable monitorTable
   * @param eventData eventData
   * @param pkColumnMeta pkColumnMeta
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-23 02:22
   */
  private void processScanRecord(
      MonitorTable monitorTable, UpdateRowsEventData eventData, ColumnMeta pkColumnMeta) {
    List<Entry<Serializable[], Serializable[]>> rows = eventData.getRows();
    for (Entry<Serializable[], Serializable[]> row : rows) {
      Serializable[] oldRow = row.getKey();
      Serializable[] newRow = row.getValue();

      if (pkColumnMeta.getPos() < oldRow.length && pkColumnMeta.getPos() < newRow.length) {
        Serializable oldId = oldRow[pkColumnMeta.getPos()];
        Serializable newId = newRow[pkColumnMeta.getPos()];
        if (oldId != null && newId != null) {
          if (oldId.equals(newId)) {

            tableMonitorTaskManager.saveOrUpdate(
                monitorTable.getDestination(),
                monitorTable.getDatabase(),
                monitorTable.getName(),
                String.valueOf(oldId),
                RowOP.UPDATE);
          } else {

            tableMonitorTaskManager.saveOrUpdate(
                monitorTable.getDestination(),
                monitorTable.getDatabase(),
                monitorTable.getName(),
                String.valueOf(oldId),
                RowOP.DELETE);

            tableMonitorTaskManager.saveOrUpdate(
                monitorTable.getDestination(),
                monitorTable.getDatabase(),
                monitorTable.getName(),
                String.valueOf(newId),
                RowOP.INSERT);
          }
        }
      }
    }
  }
}

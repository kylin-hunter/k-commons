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

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.DeleteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import java.io.Serializable;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-16 00:48
 */
@RequiredArgsConstructor
public class MonitorDeleteRowsEventDataProcessor extends DeleteRowsEventDataProcessor {

  private final TableMonitorTaskManager tableMonitorTaskManager;
  private final MonitorTables monitorTables;

  private final MonitorManager monitorManager;

  @Override
  protected void deleteDataRecord(DeleteRowsEventData eventData, Context context) {

    this.monitorManager.process(
        eventData.getTableId(), this.monitorTables, eventData, this::processScanRecord);
  }

  /**
   * @param monitorTable monitorTable
   * @param eventData    eventData
   * @param pkColumnMeta pkColumnMeta
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-23 02:20
   */
  private void processScanRecord(
      MonitorTable monitorTable, DeleteRowsEventData eventData, ColumnMeta pkColumnMeta) {

    List<Serializable[]> rows = eventData.getRows();
    for (Serializable[] row : rows) {
      if (pkColumnMeta.getPos() < row.length) {
        tableMonitorTaskManager.saveOrUpdate(
            monitorTable.getDestination(),
            monitorTable.getDatabase(),
            monitorTable.getName(),
            String.valueOf(row[pkColumnMeta.getPos()]),
            RowOP.DELETE);
      }
    }
  }
}

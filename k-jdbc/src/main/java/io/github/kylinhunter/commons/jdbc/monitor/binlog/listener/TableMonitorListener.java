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

import io.github.kylinhunter.commons.jdbc.binlog.listener.AbstractBinLogEventListener;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTables;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor.MonitorDeleteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor.MonitorManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor.MonitorUpdateRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor.MonitorWriteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import javax.sql.DataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description EventListener
 * @date 2023-11-25 02:56
 */
@Slf4j
public class TableMonitorListener extends AbstractBinLogEventListener {

  @Setter
  private MonitorTables monitorTables;
  @Setter
  TableMonitorTaskManager tableMonitorTaskManager;

  MonitorManager monitorManager;

  @Override
  public void init(DataSource dataSource) {
    super.init(dataSource);
    this.monitorManager = new MonitorManager(this.tableIdManager);
    this.context = new Context();
    if (tableMonitorTaskManager == null) {
      this.tableMonitorTaskManager = new TableMonitorTaskManager(dataSource);
    }
    for (MonitorTable monitorTable : monitorTables.getDatas()) {
      tableMonitorTaskManager.ensureDestinationExists(monitorTable.getDestination());
    }

    addEventProcessor(
        new MonitorWriteRowsEventDataProcessor(tableMonitorTaskManager, monitorTables,
            monitorManager));
    addEventProcessor(
        new MonitorDeleteRowsEventDataProcessor(tableMonitorTaskManager, monitorTables,
            monitorManager));
    addEventProcessor(
        new MonitorUpdateRowsEventDataProcessor(tableMonitorTaskManager, monitorTables,
            monitorManager));
  }
}

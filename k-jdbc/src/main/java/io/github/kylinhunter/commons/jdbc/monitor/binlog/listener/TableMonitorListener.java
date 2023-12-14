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

import com.github.shyiko.mysql.binlog.event.EventType;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.TableMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex.MonitorDeleteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex.MonitorUpdateRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.ex.MonitorWriteRowsEventDataProcessor;
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
public class TableMonitorListener extends AbstractBinLogEventListener<TableMonitorContext> {

  @Setter
  private TableMonitorConfig tableBinlogConfig;

  @Override
  public void init(DataSource dataSource) {
    super.init(dataSource);
    TableMonitorTaskManager tableMonitorTaskManager = new TableMonitorTaskManager(dataSource);
    this.context = new TableMonitorContext();
    this.context.setTableMonitorTaskManager(tableMonitorTaskManager);
    this.context.setTableMonitorConfig(tableBinlogConfig);

    tableMonitorTaskManager.ensureDestinationExists(tableBinlogConfig.getDestination());

    addEventProcessor(EventType.EXT_WRITE_ROWS, new MonitorWriteRowsEventDataProcessor());
    addEventProcessor(EventType.EXT_DELETE_ROWS, new MonitorDeleteRowsEventDataProcessor());
    addEventProcessor(EventType.EXT_UPDATE_ROWS, new MonitorUpdateRowsEventDataProcessor());

  }


}

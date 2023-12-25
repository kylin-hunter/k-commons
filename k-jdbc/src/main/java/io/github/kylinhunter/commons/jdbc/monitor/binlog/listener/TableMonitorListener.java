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
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor.ExDeleteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor.ExUpdateRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor.ExWriteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor.TableProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description EventListener
 * @date 2023-11-25 02:56
 */
@Slf4j
public class TableMonitorListener extends AbstractBinLogEventListener {

  private final BinMonitorConfig config;

  private final TableTaskManager taskManager;

  public TableMonitorListener(BinMonitorConfig config, TableTaskManager taskManager) {
    this.config = config;
    this.taskManager = taskManager;
  }

  @Override
  public void init(DataSource dataSource) {
    super.init(dataSource);
    this.context = new Context();
    for (BinTable binTable : config.getBinTables()) {
      taskManager.ensureDestinationExists(binTable.getDestination());
    }

    TableProcessor tableProcessor = new TableProcessor(this.tableIdManager, this.config);
    addEventProcessor(new ExWriteRowsEventDataProcessor(taskManager, tableProcessor));
    addEventProcessor(new ExDeleteRowsEventDataProcessor(taskManager, tableProcessor));
    addEventProcessor(new ExUpdateRowsEventDataProcessor(taskManager, tableProcessor));
  }
}

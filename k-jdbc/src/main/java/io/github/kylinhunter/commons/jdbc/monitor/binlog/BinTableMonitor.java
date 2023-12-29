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
package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.binlog.BinLogClient;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinConfig;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.TableMonitorListener;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.task.RowListener;
import io.github.kylinhunter.commons.jdbc.monitor.task.TaskProcessor;
import java.util.concurrent.Executors;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-17 17:29
 */
public class BinTableMonitor implements TableMonitor {

  private final BinLogClient binLogClient;

  private final TaskProcessor taskProcessor;

  private final BinMonitorConfig monitorConfig;

  private final TableTaskManager taskManager;

  public BinTableMonitor(BinConfig binConfig, BinMonitorConfig monitorConfig) {
    this.binLogClient = new BinLogClient(binConfig);
    this.monitorConfig = monitorConfig;
    DataSource dataSource = binLogClient.getDataSource();
    this.taskManager = new TableTaskManager(dataSource);

    this.binLogClient.addBinLogEventListener(new TableMonitorListener(monitorConfig, taskManager));
    this.taskProcessor = new BinTaskProcessor(taskManager, monitorConfig);
    this.taskProcessor.setScheduler(Executors.newScheduledThreadPool(
        monitorConfig.getThreadPoolSize()));
  }

  /**
   * @param rowListener rowListener
   * @title setRowListener
   * @description setRowListener
   * @author BiJi'an
   * @date 2023-12-25 23:15
   */
  public void setRowListener(RowListener rowListener) {

    taskProcessor.setRowListener(rowListener);
  }

  @Override
  public void shutdown() {
    this.binLogClient.disconnect();
    this.taskProcessor.shutdown();
  }

  /**
   * @title start
   * @description start
   * @author BiJi'an
   * @date 2023-12-17 00:40
   */
  @Override
  public void start() {

    taskProcessor.start();
    binLogClient.start();
  }

  /**
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-17 00:41
   */
  @Override
  public void reset() {
    binLogClient.reset();
    for (BinTable binTable : monitorConfig.getBinTables()) {
      this.taskManager.clean(
          binTable.getDestination(), binTable.getDatabase(), binTable.getTableName());
    }
  }
}

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
import io.github.kylinhunter.commons.jdbc.binlog.BinLogConfig;
import java.util.List;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-17 17:29
 */
public class BinLogTableMonitor {

  @Setter
  private List<MonitorTable> monitorTables;
  private final BinLogClient binLogClient;

  private final TableMonitorListener tableMonitorListener = new TableMonitorListener();

  public BinLogTableMonitor(BinLogConfig binLogConfig) {
    this.binLogClient = new BinLogClient(binLogConfig);
  }

  public void start() {
    tableMonitorListener.setMonitorTables(monitorTables);
    binLogClient.addBinLogEventListener(tableMonitorListener);
    binLogClient.start();
  }

  public void reset() {
    binLogClient.reset();
  }
}

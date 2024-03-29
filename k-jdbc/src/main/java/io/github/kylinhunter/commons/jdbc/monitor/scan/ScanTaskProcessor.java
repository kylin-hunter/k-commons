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
package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.TableScanConfig;
import io.github.kylinhunter.commons.jdbc.monitor.task.AbstractTaskProcessor;
import java.util.ArrayList;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-23 20:23
 */
public class ScanTaskProcessor extends AbstractTaskProcessor {

  private final TableScanConfig tableScanConfig;

  public ScanTaskProcessor(DataSource dataSource, TableScanConfig tableScanConfig) {
    this.taskManager = new TableTaskManager(dataSource);
    this.tableScanConfig = tableScanConfig;
    this.config = tableScanConfig;
    this.tables = new ArrayList<>(tableScanConfig.getScanTables());
  }
}

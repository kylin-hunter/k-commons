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

import io.github.kylinhunter.commons.jdbc.dao.AbstractDatabaseVisitor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.manager.ScanProgressManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.ScanRecordManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import io.github.kylinhunter.commons.util.ThreadHelper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:48
 */
@Slf4j
public class TableScanManager extends AbstractDatabaseVisitor {

  private ScanProgressManager scanProgressManager;
  private TableMonitorTaskManager tableMonitorTaskManager;

  private ScanRecordManager scanRecordManager;

  private TableScanConfig tableScanConfig;

  private ScheduledExecutorService scheduler;

  public TableScanManager() {
    super(null, true);
    init();
  }

  public TableScanManager(DataSource dataSource) {
    super(dataSource, false);
    init();
  }

  private void init() {
    this.scanProgressManager = new ScanProgressManager(dataSource);
    this.tableMonitorTaskManager = new TableMonitorTaskManager(dataSource);
    this.scanRecordManager = new ScanRecordManager(dataSource);

  }

  public void start() {
    this.scheduler = Executors.newScheduledThreadPool(tableScanConfig.getScheduleCorePoolSize());

    for (ScanTable scanTable : tableScanConfig.getScanTables()) {
      scanTable.setConfig(tableScanConfig);
      scan(scanTable);
    }
  }

  /**
   * @param scanTable scanTable
   * @title scan
   * @description scan
   * @author BiJi'an
   * @date 2023-12-18 20:19
   */
  private void scan(ScanTable scanTable) {

    if (scanTable.getScanInterval() > 0) {
      this.scheduler.scheduleWithFixedDelay(() -> TableScanManager.this.run(scanTable), 0,
          scanTable.getScanInterval(), TimeUnit.MILLISECONDS);

    } else {
      run(scanTable);
    }
  }

  /**
   * @param scanTable tableScanConfig
   * @title run
   * @description run
   * @author BiJi'an
   * @date 2023-12-16 22:50
   */
  private void run(ScanTable scanTable) {
    try {
      processSameTime(scanTable);
      processNextTime(scanTable);
    } catch (Exception e) {
      log.error("scan error", e);
    }
  }

  /**
   * @param scanTable tableScanConfig
   * @title processSameTime
   * @description processSameTime
   * @author BiJi'an
   * @date 2023-12-16 23:06
   */
  private void processSameTime(ScanTable scanTable) {

    while (true) {

      ScanProgress scanProgress = scanProgressManager.getLatestScanProgress(scanTable);
      List<ScanRecord> scanRecords = scanRecordManager.scanSameTime(scanTable, scanProgress);
      if (scanRecords.size() == 0) {
        break;
      } else {
        scanRecords.forEach(scanRecord -> log.info(" process same time data:" + scanRecord));
        for (ScanRecord scanRecord : scanRecords) {
          tableMonitorTaskManager.saveOrUpdate(scanTable, scanRecord);
        }

        ScanRecord lastRecord = scanRecords.get(scanRecords.size() - 1);
        this.scanProgressManager.update(scanTable.getServerId(), scanTable.getTableName(),
            lastRecord);
      }
      ThreadHelper.sleep(scanTable.getScanInterval(), TimeUnit.MILLISECONDS);
    }
  }

  /**
   * @param scanTable tableScanConfig
   * @title proccessNextTime
   * @description proccessNextTime
   * @author BiJi'an
   * @date 2023-12-09 02:06
   */
  private void processNextTime(ScanTable scanTable) {

    ScanProgress scanProgress = scanProgressManager.getLatestScanProgress(scanTable);
    LocalDateTime nextScanTime = scanProgress.getNextScanTime();
    List<ScanRecord> scanRecords = scanRecordManager.scanNextTime(scanTable, nextScanTime);
    if (scanRecords.size() > 0) {
      scanRecords.forEach(scanRecord -> log.info(" process next time data:" + scanRecord));
      ScanRecord lastRecord = scanRecords.get(scanRecords.size() - 1);
      for (ScanRecord scanRecord : scanRecords) {
        tableMonitorTaskManager.saveOrUpdate(scanTable, scanRecord);
      }
      this.scanProgressManager.update(scanTable.getServerId(), scanTable.getTableName(),
          lastRecord);
    }
  }

  /**
   * @param config config
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-09 16:58
   */
  public void init(TableScanConfig config) {
    Objects.requireNonNull(config);
    this.tableScanConfig = config;
    this.scanProgressManager.ensureTableExists();
    config
        .getScanTables()
        .forEach(
            scanTable -> this.tableMonitorTaskManager.ensureDestinationExists(
                scanTable.getDestination()));
  }

  /**
   * @param config config
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-16 23:51
   */
  public void clean(ScanTable config) {
    this.scanProgressManager.delete(config.getServerId(), config.getTableName());
    this.tableMonitorTaskManager.clean(config.getDestination(), config.getTableName());
  }
}

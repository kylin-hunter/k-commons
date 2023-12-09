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
package io.github.kylinhunter.commons.jdbc.scan;

import io.github.kylinhunter.commons.exception.embed.UnsupportedException;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.scan.bean.ScanRecord;
import io.github.kylinhunter.commons.jdbc.scan.dao.ScanProcessorDAO;
import io.github.kylinhunter.commons.jdbc.scan.dao.ScanProgressDAO;
import io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProcessor;
import io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.scan.dao.imp.MysqlScanProcessorDAO;
import io.github.kylinhunter.commons.jdbc.scan.dao.imp.MysqlScanProgressDAO;
import io.github.kylinhunter.commons.util.ThreadHelper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:48
 */
@Slf4j
public class TableScanManager {

  private final ScanProgressDAO scanProgressDAO;
  private final ScanProcessorDAO scanProcessorDAO;
  private final BeanListHandler<ScanRecord> beanHandler = new BeanListHandler<>(ScanRecord.class);
  private final ScheduledExecutorService scheduledExecutorService;
  private static final String SAME_SQL =
      "select %s as id ,%s as time from  %s "
          + " where %s = ? and %s > ? order by %s asc, %s asc limit ?";

  private static final String NEXT_SQL =
      "select %s as id ,%s as time from %s "
          + " where %s > ?  and %s<? order by %s asc, %s asc limit ?";

  public TableScanManager() {
    this(DbType.MYSQL, null);
  }

  public TableScanManager(DbType dbType) {
    this(dbType, null);
  }

  public TableScanManager(DbType dbType, DataSource dataSource) {
    if (dbType == DbType.MYSQL) {
      this.scanProgressDAO = new MysqlScanProgressDAO(dataSource);
      this.scanProcessorDAO = new MysqlScanProcessorDAO(dataSource);
    } else {
      throw new UnsupportedException("unsupported dbType=" + dbType);
    }
    this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
  }

  /**
   * @param tableScan scanOption
   * @title scan
   * @description scan
   * @author BiJi'an
   * @date 2023-12-09 02:03
   */
  public void scan(TableScan tableScan) {

    Objects.requireNonNull(tableScan);
    Runnable run =
        () -> {
          try {
            processSameTime(tableScan);
            processNextTime(tableScan);
          } catch (Exception e) {
            log.error("scan error", e);
          }
        };
    if (tableScan.isDaemon()) {
      scheduledExecutorService.schedule(run, 0, TimeUnit.SECONDS);
    } else {
      run.run();
    }
  }

  /**
   * @title proccessSameData
   * @description proccessSameData
   * @author BiJi'an
   * @date 2023-12-09 00:48
   */
  private void processSameTime(TableScan tableScan) {
    SqlExecutor sqlExecutor = this.scanProcessorDAO.getSqlExecutor();

    while (true) {
      String sql =
          String.format(
              SAME_SQL,
              tableScan.getTableIdColName(),
              tableScan.getTableTimeColName(),
              tableScan.getTableName(),
              tableScan.getTableTimeColName(),
              tableScan.getTableIdColName(),
              tableScan.getTableTimeColName(),
              tableScan.getTableIdColName());
      ScanProgress scanProgress = this.getLatestScanProgress(tableScan);
      List<ScanRecord> scanRecords =
          sqlExecutor.query(
              sql,
              beanHandler,
              scanProgress.getNextScanTime(),
              scanProgress.getLastScanId(),
              tableScan.getScanLimit());

      if (scanRecords.size() > 0) {
        scanRecords.forEach(scanRecord -> log.info(" process same time data:" + scanRecord));

        ScanRecord last = scanRecords.get(scanRecords.size() - 1);
        ScanProgress nextScanProgress = new ScanProgress(tableScan.getTableName(), last);
        for (ScanRecord scanRecord : scanRecords) {
          processScanRecord(tableScan, scanRecord);
        }
        this.scanProgressDAO.update(nextScanProgress);
      } else {
        break;
      }
      ThreadHelper.sleep(tableScan.getScanSameTimeInterval(), TimeUnit.MILLISECONDS);
    }
  }

  /**
   * @param tableScan tableScan
   * @param scanRecord scanRecord
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-09 15:13
   */
  private void processScanRecord(TableScan tableScan, ScanRecord scanRecord) {
    ScanProcessor scanProcessor =
        this.scanProcessorDAO.findById(
            tableScan.getSaveDestination(), tableScan.getTableName(), scanRecord.getId());
    if (scanProcessor == null) {
      scanProcessor = new ScanProcessor();
      scanProcessor.setId(tableScan.getTableName());
      scanProcessor.setDataId(scanRecord.getId());
      scanProcessor.setStatus(0);
      scanProcessor.setOp(1);
      this.scanProcessorDAO.save(tableScan.getSaveDestination(), scanProcessor);
    } else {
      scanProcessor.setStatus(0);
      scanProcessor.setOp(1);
      this.scanProcessorDAO.update(tableScan.getSaveDestination(), scanProcessor);
    }
  }

  /**
   * @param tableScan scanOption
   * @title proccessNextTime
   * @description proccessNextTime
   * @author BiJi'an
   * @date 2023-12-09 02:06
   */
  private void processNextTime(TableScan tableScan) {
    SqlExecutor sqlExecutor = this.scanProcessorDAO.getSqlExecutor();

    ScanProgress scanProgress = this.getLatestScanProgress(tableScan);
    LocalDateTime nextScanTime = scanProgress.getNextScanTime();
    LocalDateTime endTime = LocalDateTime.now().minus(3, ChronoUnit.SECONDS);
    String sql =
        String.format(
            NEXT_SQL,
            tableScan.getTableIdColName(),
            tableScan.getTableTimeColName(),
            tableScan.getTableName(),
            tableScan.getTableTimeColName(),
            tableScan.getTableTimeColName(),
            tableScan.getTableTimeColName(),
            tableScan.getTableIdColName());

    List<ScanRecord> scanRecords =
        sqlExecutor.query(sql, beanHandler, nextScanTime, endTime, tableScan.getScanLimit());

    if (scanRecords.size() > 0) {
      scanRecords.forEach(scanRecord -> log.info(" process next time data:" + scanRecord));
      ScanRecord last = scanRecords.get(scanRecords.size() - 1);
      ScanProgress nextScanProgress = new ScanProgress(tableScan.getTableName(), last);
      for (ScanRecord scanRecord : scanRecords) {
        processScanRecord(tableScan, scanRecord);
      }
      this.scanProgressDAO.update(nextScanProgress);
    }
  }

  /**
   * @title createScanProgress
   * @description createScanProgress
   * @author BiJi'an
   * @date 2023-12-08 21:17
   */
  private ScanProgress getLatestScanProgress(TableScan tableScan) {

    ScanProgress progress = this.scanProgressDAO.findById(tableScan.getTableName());
    if (progress == null) {
      progress =
          new ScanProgress(
              tableScan.getTableName(),
              tableScan.getSaveDestination(),
              tableScan.getFirstScanTime(),
              "");
      this.scanProgressDAO.save(progress);
    }
    return progress;
  }

  /**
   * @param tableScan tableScan
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-09 16:58
   */
  public void init(TableScan tableScan) {
    this.scanProgressDAO.ensureTableExists();
    this.scanProcessorDAO.ensureTableExists(tableScan.getSaveDestination());
  }

  public void shutdown() {
    this.scheduledExecutorService.shutdown();
  }

  public void clean(TableScan tableScan) {
    this.scanProgressDAO.delete(tableScan.getTableName());
    this.scanProcessorDAO.clean(tableScan.getSaveDestination(), tableScan.getTableName());
  }
}

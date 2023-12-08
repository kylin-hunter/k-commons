package io.github.kylinhunter.commons.jdbc.scan;

import io.github.kylinhunter.commons.exception.embed.UnsupportedException;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.scan.bean.ScanRecord;
import io.github.kylinhunter.commons.jdbc.scan.dao.MysqlScanProcessDAO;
import io.github.kylinhunter.commons.jdbc.scan.dao.ScanProcessDAO;
import io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProgress;
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

  private final ScanProcessDAO scanProcessDAO;
  private final BeanListHandler<ScanRecord> beanHandler = new BeanListHandler<>(ScanRecord.class);
  private final ScheduledExecutorService scheduledExecutorService;
  private static final String SAME_SQL = "select %s as id ,%s as time from  %s "
      + " where %s = ? and %s > ? order by %s asc, %s asc limit ?";

  private static final String NEXT_SQL = "select %s as id ,%s as time from %s "
      + " where %s > ?  and %s<? order by %s asc, %s asc limit ?";

  public TableScanManager() {
    this(DbType.MYSQL, null);
  }

  public TableScanManager(DbType dbType) {
    this(dbType, null);
  }

  public TableScanManager(DbType dbType, DataSource dataSource) {
    if (dbType == DbType.MYSQL) {
      this.scanProcessDAO = new MysqlScanProcessDAO(dataSource);
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

    Runnable run = () -> {
      try {
        processSameTime(tableScan);
        processNextTime(tableScan);
      } catch (Exception e) {
        log.error("scan error", e);
      }
    };
    scheduledExecutorService.scheduleWithFixedDelay(run, 0, tableScan.getScanInterval(),
        TimeUnit.MILLISECONDS);


  }


  /**
   * @title proccessSameData
   * @description proccessSameData
   * @author BiJi'an
   * @date 2023-12-09 00:48
   */
  private void processSameTime(TableScan tableScan) {
    SqlExecutor sqlExecutor = this.scanProcessDAO.getSqlExecutor();

    while (true) {
      String sql = String.format(SAME_SQL, tableScan.getTableIdColName(),
          tableScan.getTableTimeColName(), tableScan.getTableName(),
          tableScan.getTableTimeColName(), tableScan.getTableIdColName(),
          tableScan.getTableTimeColName(), tableScan.getTableIdColName());
      ScanProgress scanProgress = this.getLatestScanProgress(tableScan);
      List<ScanRecord> scanRecords = sqlExecutor.query(sql, beanHandler,
          scanProgress.getNextScanTime(), scanProgress.getLastScanId(), tableScan.getScanLimit());

      if (scanRecords.size() > 0) {
        scanRecords.forEach(scanRecord -> log.info(" process same time data:" + scanRecord));

        ScanRecord last = scanRecords.get(scanRecords.size() - 1);
        ScanProgress nextScanProgress = new ScanProgress(tableScan.getTableName(), last);
        this.scanProcessDAO.update(nextScanProgress);
      } else {
        break;
      }
      ThreadHelper.sleep(tableScan.getScanSameTimeInterval(), TimeUnit.MILLISECONDS);

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
    SqlExecutor sqlExecutor = this.scanProcessDAO.getSqlExecutor();

    ScanProgress scanProgress = this.getLatestScanProgress(tableScan);
    LocalDateTime nextScanTime = scanProgress.getNextScanTime();
    LocalDateTime endTime = LocalDateTime.now().minus(3, ChronoUnit.SECONDS);
    String sql = String.format(NEXT_SQL, tableScan.getTableIdColName(),
        tableScan.getTableTimeColName(), tableScan.getTableName(), tableScan.getTableTimeColName(),
        tableScan.getTableTimeColName(), tableScan.getTableTimeColName(),
        tableScan.getTableIdColName());

    List<ScanRecord> scanRecords = sqlExecutor.query(sql, beanHandler, nextScanTime, endTime,
        tableScan.getScanLimit());

    if (scanRecords.size() > 0) {
      scanRecords.forEach(scanRecord -> log.info(" process next time data:" + scanRecord));
      ScanRecord last = scanRecords.get(scanRecords.size() - 1);
      ScanProgress nextScanProgress = new ScanProgress(tableScan.getTableName(), last);
      this.scanProcessDAO.update(nextScanProgress);

    }

  }


  /**
   * @title createScanProgress
   * @description createScanProgress
   * @author BiJi'an
   * @date 2023-12-08 21:17
   */
  private ScanProgress getLatestScanProgress(TableScan tableScan) {

    ScanProgress progress = scanProcessDAO.findById(tableScan.getTableName());
    if (progress == null) {
      progress = new ScanProgress(tableScan.getTableName(), tableScan.getSaveDestination(),
          tableScan.getFirstScanTime(), "");
      scanProcessDAO.save(progress);
    }
    return progress;

  }


  /**
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-09 20:19
   */
  public void init() {

    this.scanProcessDAO.ensureTableExists();

  }

}
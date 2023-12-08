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
  private final BeanListHandler<ScanRecord> beanListHandler = new BeanListHandler<>(
      ScanRecord.class);

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
  }

  /**
   * @param tableName tableName
   * @title scan
   * @description scan
   * @author BiJi'an
   * @date 2023-12-09 02:03
   */
  public void scan(String tableName) {
    scan(tableName, new ScanOption());
  }

  /**
   * @param tableName  tableName
   * @param scanOption scanOption
   * @title scan
   * @description scan
   * @author BiJi'an
   * @date 2023-12-09 02:03
   */
  public void scan(String tableName, ScanOption scanOption) {
    Objects.requireNonNull(scanOption);
    while (true) {
      try {
        proccessSameTime(tableName, scanOption);
        proccessNextTime(tableName, scanOption);
        ThreadHelper.sleep(scanOption.getScanInterval(), TimeUnit.MILLISECONDS);
      } catch (Exception e) {
        log.error("scan error", e);
      }
    }

  }


  /**
   * @param tableName tableName
   * @title proccessSameData
   * @description proccessSameData
   * @author BiJi'an
   * @date 2023-12-09 00:48
   */
  private void proccessSameTime(String tableName, ScanOption scanOption) {
    SqlExecutor sqlExecutor = this.scanProcessDAO.getSqlExecutor();

    String sql = "select id as id ,sys_auto_updated as time from " + tableName
        + " where sys_auto_updated = ? and id > ? order by sys_auto_updated asc, id asc limit 2";

    while (true) {
      ScanProgress scanProgress = this.getScanProgress(tableName, scanOption);
      List<ScanRecord> scanRecords = sqlExecutor.query(sql, beanListHandler,
          scanProgress.getNextScanTime(), scanProgress.getLastScanId());

      if (scanRecords.size() > 0) {
        scanRecords.forEach(scanRecord -> {
          log.info(" process same time data:" + scanRecord);
        });

        ScanRecord last = scanRecords.get(scanRecords.size() - 1);
        ScanProgress nextScanProgress = new ScanProgress(tableName);
        nextScanProgress.setNextScanTime(last.getTime());
        nextScanProgress.setLastScanId(last.getId());
        this.scanProcessDAO.update(nextScanProgress);

      } else {
        break;
      }
    }

  }

  /**
   * @param tableName  tableName
   * @param scanOption scanOption
   * @title proccessNextTime
   * @description proccessNextTime
   * @author BiJi'an
   * @date 2023-12-09 02:06
   */
  private void proccessNextTime(String tableName, ScanOption scanOption) {
    SqlExecutor sqlExecutor = this.scanProcessDAO.getSqlExecutor();

    String sql = "select id as id ,sys_auto_updated as time from " + tableName
        + " where sys_auto_updated > ?  and sys_auto_updated<? order by sys_auto_updated asc, id asc limit ?";
    ScanProgress scanProgress = this.getScanProgress(tableName, scanOption);
    LocalDateTime nextScanTime = scanProgress.getNextScanTime();
    LocalDateTime endTime = LocalDateTime.now().minus(3, ChronoUnit.SECONDS);

    List<ScanRecord> scanRecords = sqlExecutor.query(sql, beanListHandler, nextScanTime, endTime,
        scanOption.getScanLimit());

    if (scanRecords.size() > 0) {
      scanRecords.forEach(scanRecord -> {
        log.info(" process next time data:" + scanRecord);
      });
      ScanRecord last = scanRecords.get(scanRecords.size() - 1);
      ScanProgress nextScanProgress = new ScanProgress(tableName);
      nextScanProgress.setNextScanTime(last.getTime());
      nextScanProgress.setLastScanId(last.getId());
      this.scanProcessDAO.update(nextScanProgress);

    }
  }


  /**
   * @title createScanProgress
   * @description createScanProgress
   * @author BiJi'an
   * @date 2023-12-08 21:17
   */
  private ScanProgress getScanProgress(String tableName, ScanOption scanOption) {

    ScanProgress progress = scanProcessDAO.findById(tableName);
    if (progress == null) {
      progress = new ScanProgress(tableName, scanOption.getSaveDestination(),
          scanOption.getFirstScanTime(), "");
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
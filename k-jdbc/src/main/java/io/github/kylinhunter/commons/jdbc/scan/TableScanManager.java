package io.github.kylinhunter.commons.jdbc.scan;

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
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

  private ScanProcessDAO scanProcessDAO;
  private DbType dbType;
  private BeanListHandler<ScanProgress> beanListHandler = new BeanListHandler<>(
      ScanProgress.class);

  public TableScanManager(DbType dbType) {
    this(dbType, null);
  }

  public TableScanManager(DbType dbType, DataSource dataSource) {
    if (dbType == DbType.MYSQL) {
      this.scanProcessDAO = new MysqlScanProcessDAO(dataSource);
    }
  }


  public void scan(String tableName) {
    scan(tableName, new ScanOption());
  }

  /**
   * @param tableName tableName x   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-09 20:41
   */
  public void scan(String tableName, ScanOption scanOption) {

    Objects.requireNonNull(scanOption);

    LocalDateTime endTime = LocalDateTime.now().minus(3, ChronoUnit.SECONDS);

    String sql = "select id as lastScanId ,sys_auto_updated as nextScanTime from " + tableName
        + " where sys_auto_updated >= ?  and sys_auto_updated<? order by sys_auto_updated asc, id asc limit ?";

    SqlExecutor sqlExecutor = this.scanProcessDAO.getSqlExecutor();

    while (true) {

      ScanProgress scanProgress = this.getScanProgress(tableName, scanOption);

      LocalDateTime nextScanTime = scanProgress.getNextScanTime();
      String lastScanId = scanProgress.getLastScanId();

      List<ScanProgress> beans = sqlExecutor.query(sql, beanListHandler, nextScanTime, endTime,
          scanOption.getScanLimit());

      if (beans.size() > 0) {
        beans.forEach(System.out::println);

        ScanProgress first = beans.get(0);
        ScanProgress last = beans.get(beans.size() - 1);
        ScanProgress newScanProgress = new ScanProgress();
        newScanProgress.setId(tableName);
        newScanProgress.setNextScanTime(last.getNextScanTime());
        newScanProgress.setLastScanId(last.getLastScanId());
        if (first.getNextScanTime().equals(last.getNextScanTime())) {
          proccessSameData(last.getNextScanTime(), last.getLastScanId());
          newScanProgress.setNextScanTime(nextScanTime.plus(1, ChronoUnit.SECONDS));
        }

        this.scanProcessDAO.update(newScanProgress);


      } else {
        log.info(" no data");
      }

      ThreadHelper.sleep(1, TimeUnit.SECONDS);


    }

  }

  /**
   * @return void
   * @throws
   * @title createScanProgress
   * @description createScanProgress
   * @author BiJi'an
   * @date 2023-12-08 21:17
   */
  private ScanProgress getScanProgress(String tableName, ScanOption scanOption) {

    ScanProgress progress = scanProcessDAO.findById(tableName);
    if (progress == null) {
      progress = new ScanProgress(tableName, scanOption.getSaveDestination(),
          scanOption.getFirstScanTime(),
          scanOption.getLastScanId());
      scanProcessDAO.save(progress);
    }
    return progress;

  }

  private void proccessSameData(LocalDateTime nextScanTime, String lastScanId) {
    SqlExecutor sqlExecutor = this.scanProcessDAO.getSqlExecutor();
    final BeanListHandler<ScanProgress> beanListHandler = new BeanListHandler<>(
        ScanProgress.class);

    String sql = "select id as lastScanId ,sys_auto_updated as nextScanTime from k_jdbc_test_role"
        + " where sys_auto_updated = ? and id > ? order by sys_auto_updated asc, id asc limit 2";

    while (true) {
      List<ScanProgress> beans = sqlExecutor.query(sql, beanListHandler, nextScanTime, lastScanId);

      if (beans.size() > 0) {
        beans.forEach(e -> {
          System.out.println("same data:" + e);
        });

        ScanProgress last = beans.get(beans.size() - 1);
        nextScanTime = last.getNextScanTime();
        lastScanId = last.getLastScanId();

      } else {
        break;
      }
    }

  }

  /**
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-09 20:19
   */
  public void init() {

    this.scanProcessDAO.init();

  }

}
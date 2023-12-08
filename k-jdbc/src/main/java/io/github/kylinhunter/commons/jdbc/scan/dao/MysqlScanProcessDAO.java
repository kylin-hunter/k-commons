package io.github.kylinhunter.commons.jdbc.scan.dao;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.execute.SqlFileReader;
import io.github.kylinhunter.commons.jdbc.meta.AbstractDatabaseManager;
import io.github.kylinhunter.commons.jdbc.meta.MetaReaderFactory;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProgress;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-03 19:50
 */
public class MysqlScanProcessDAO extends AbstractDatabaseManager implements
    ScanProcessDAO {

  private static final String INIT_SQL = "io/github/kylinhunter/commons/jdbc/scan/scan-mysql.sql";
  private static final String TABLE_SCAN_PROGRESS = "k_table_scan_progress";

  private static final String INSERT_SQL = "insert into " + TABLE_SCAN_PROGRESS
      + " (id, save_destination, next_scan_time,last_scan_id) values(?,?,?,?)";


  private static final String UPDATE_SQL = "update " + TABLE_SCAN_PROGRESS
      + " set next_scan_time=?,last_scan_id=? where id=?";


  private static final String SELECT_SQL = "select  id, save_destination saveDestination, "
      + " next_scan_time nextScanTime,last_scan_id lastScanId  from " + TABLE_SCAN_PROGRESS
      + " where id=?";
  private final BeanListHandler<ScanProgress> beanListHandler = new BeanListHandler<>(
      ScanProgress.class);

  private final TableReader tableReader;

  public MysqlScanProcessDAO() {
    this(null);

  }

  public MysqlScanProcessDAO(DataSource dataSource) {
    super(dataSource);
    this.dbType = DbType.MYSQL;
    this.tableReader = MetaReaderFactory.getTableMetaReader(this.dbType);
  }


  public void save(ScanProgress scanProgress) {

    this.getSqlExecutor()
        .execute(INSERT_SQL, scanProgress.getId(), scanProgress.getSaveDestination(),
            scanProgress.getNextScanTime(), scanProgress.getLastScanId());

  }

  @Override
  public void update(ScanProgress scanProgress) {
    this.getSqlExecutor().execute(UPDATE_SQL,
        scanProgress.getNextScanTime(), scanProgress.getLastScanId(), scanProgress.getId());
  }

  /**
   * @param id id
   * @return io.github.kylinhunter.commons.jdbc.scan.bean.TableScanProgress
   * @title getTableScanProgress
   * @description getTableScanProgress
   * @author BiJi'an
   * @date 2023-12-09 22:42
   */
  public ScanProgress findById(String id) {

    List<ScanProgress> scanProgresses = this.getSqlExecutor()
        .query(SELECT_SQL, beanListHandler, id);
    if (!CollectionUtils.isEmpty(scanProgresses)) {
      return scanProgresses.get(0);
    }
    return null;


  }

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:16
   */
  @Override
  public void ensureTableExists() {
    boolean exist = this.tableReader.exist(this.getDataSource(), "",
        TABLE_SCAN_PROGRESS);
    if (!exist) {
      List<String> sqlLines = SqlFileReader.read(INIT_SQL);
      this.getSqlExecutor().execute(sqlLines, true);
    }

  }

}
package io.github.kylinhunter.commons.jdbc.binlog;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.jdbc.binlog.bean.SavePoint;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.execute.SqlFileReader;
import io.github.kylinhunter.commons.jdbc.meta.AbstractDatabaseManager;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */

public class DefaultSavePointManager extends AbstractDatabaseManager implements SavePointManager {

  public static final String TABLE_NAME = "k_jdbc_monitor_tables";
  private final static String SELECT_SQL =
      "select name,position from  " + TABLE_NAME + " where name=?";

  private final static String SELECT_SQL_LATEST = "select name,position from  " + TABLE_NAME
      + "  order by auto_updated desc,name desc limit 1 ";

  private final static String DELETE_SQL = "delete from  " + TABLE_NAME + "  where name=?";
  private final static String UPDATE_SQL =
      "update   " + TABLE_NAME + "  set position=? where name=?";
  private final static String INSERT_SQL =
      "insert into " + TABLE_NAME + "(name, position) values(?,?)";
  private final static String INIT_SQL_PATH = "io/github/kylinhunter/commons/jdbc/binlog/binlog.sql";

  private final SqlExecutor sqlExecutor;


  private final BeanListHandler<SavePoint> beanListHandler = new BeanListHandler<>(SavePoint.class);


  public DefaultSavePointManager() {
    this.defaultDataSource = DataSourceUtils.getByNo(1);
    this.sqlExecutor = new SqlExecutor(this.defaultDataSource);
    this.init();
  }

  public DefaultSavePointManager(DataSource dataSource) {
    super(dataSource);
    this.sqlExecutor = new SqlExecutor(this.defaultDataSource);
    this.init();
  }

  @Override
  public void delete(String fileName) {

    this.sqlExecutor.execute(DELETE_SQL, new Object[]{fileName});

  }

  @Override
  public void saveOrUpdate(SavePoint savePoint) {

    String name = savePoint.getName();
    SavePoint oldSavePoint = this.get(name);
    long position = savePoint.getPosition();
    if (oldSavePoint != null) {
      this.sqlExecutor.execute(UPDATE_SQL, new Object[]{position, name});
    } else {
      this.sqlExecutor.execute(INSERT_SQL, new Object[]{name, position});
    }

  }

  @Override
  public SavePoint get(String fileName) {

    List<SavePoint> savePoints = this.sqlExecutor.query(SELECT_SQL, beanListHandler,
        new Object[]{fileName});
    if (!CollectionUtils.isEmpty(savePoints)) {
      return savePoints.get(0);
    }
    return null;

  }

  @Override
  public SavePoint getLatest() {
    List<SavePoint> savePoints = this.sqlExecutor.query(SELECT_SQL_LATEST, beanListHandler);
    if (!CollectionUtils.isEmpty(savePoints)) {
      return savePoints.get(0);
    }
    return null;
  }

  @Override
  public void init() {
    List<String> sqlLines = SqlFileReader.read(INIT_SQL_PATH);
    this.sqlExecutor.execute(sqlLines, true);
  }
}
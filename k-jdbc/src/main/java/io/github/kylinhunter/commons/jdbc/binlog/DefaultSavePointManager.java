package io.github.kylinhunter.commons.jdbc.binlog;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.datasource.ExDataSource;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.execute.SqlFileReader;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */
public class DefaultSavePointManager implements SavePointManager {

  public static final String TABLE_NAME = "k_jdbc_monitor_tables";
  private final static String SELECT_SQL = "select name,position from  k_jdbc_monitor_tables where name=?";
  private final static String DELETE_SQL = "delete from  k_jdbc_monitor_tables where name=?";
  private final static String UPDATE_SQL = "update   k_jdbc_monitor_tables set position=? where name=?";
  private final static String INSERT_SQL = "insert into k_jdbc_monitor_tables(name, position) values(?,?)";


  @Override
  public void delete(String fileName) {
    try {
      ExDataSource dataSource = DataSourceUtils.getDefaultDataSource();
      QueryRunner runner = new QueryRunner(dataSource);
      runner.execute(DELETE_SQL, new Object[]{fileName});
    } catch (SQLException e) {
      throw new JdbcException(e);
    }
  }

  @Override
  public void saveOrUpdate(SavePoint savePoint) {
    try {
      ExDataSource dataSource = DataSourceUtils.getDefaultDataSource();
      QueryRunner runner = new QueryRunner(dataSource);

      SavePoint oldSavePoint = this.get(savePoint.getName());
      if (oldSavePoint != null) {
        runner.execute(UPDATE_SQL, new Object[]{savePoint.getPosition(), savePoint.getName()});

      } else {
        runner.execute(INSERT_SQL, new Object[]{savePoint.getName(), savePoint.getPosition()});

      }


    } catch (SQLException e) {
      throw new JdbcException(e);
    }
  }

  @Override
  public SavePoint get(String fileName) {
    try {
      ExDataSource dataSource = DataSourceUtils.getDefaultDataSource();

      QueryRunner runner = new QueryRunner(dataSource);
      BeanListHandler<SavePoint> beanListHandler = new BeanListHandler<>(SavePoint.class);
      List<SavePoint> savePoints = runner.query(SELECT_SQL, beanListHandler,
          new Object[]{fileName});
      if (!CollectionUtils.isEmpty(savePoints)) {
        return savePoints.get(0);
      }
      return null;
    } catch (SQLException e) {
      throw new JdbcException(e);
    }
  }

  @Override
  public void init() {
    try {
      ExDataSource dataSource = DataSourceUtils.getDefaultDataSource();
      QueryRunner runner = new QueryRunner(dataSource);

      List<String> sqlLines = SqlFileReader.read(
          "io/github/kylinhunter/commons/jdbc/binlog/binlog.sql");

      for (String sqlLine : sqlLines) {
        runner.execute(sqlLine);
      }
    } catch (SQLException e) {
      throw new JdbcException("init error", e);
    }


  }
}
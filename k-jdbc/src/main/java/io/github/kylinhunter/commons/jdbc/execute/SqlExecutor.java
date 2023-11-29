package io.github.kylinhunter.commons.jdbc.execute;

import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.dbutils.QueryRunner;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-29 14:30
 */

@RequiredArgsConstructor
public class SqlExecutor {

  @Getter
  private QueryRunner queryRunner;
  private DataSource dataSource;

  public SqlExecutor(DataSource dataSource) {
    this.dataSource = dataSource;
    this.queryRunner = new QueryRunner(dataSource);
  }

  /**
   * @param sql    sql
   * @param params params
   * @return int
   * @throws
   * @title execute
   * @description execute
   * @author BiJi'an
   * @date 2023-11-29 14:48
   */
  public int execute(String sql, Object... params) throws SQLException {
    return queryRunner.execute(sql, params);
  }


  /**
   * @param sqlLines sqlLines
   * @return void
   * @throws
   * @title execute with QueryRunner
   * @description execute
   * @author BiJi'an
   * @date 2023-11-29 14:45
   */
  public void execute(List<String> sqlLines) {
    try {
      QueryRunner runner = new QueryRunner(dataSource);
      for (String sqlLine : sqlLines) {
        runner.execute(sqlLine);
      }
    } catch (SQLException e) {
      throw new JdbcException("execute sql error", e);
    }

  }

}
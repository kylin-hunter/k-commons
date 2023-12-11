package io.github.kylinhunter.commons.jdbc.execute;

import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SqlExecutorTest {

  @Test
  void execute() throws SQLException {
    DataSourceManager dataSourceManager = new DataSourceManager();
    SqlExecutor sqlExecutor = new SqlExecutor(dataSourceManager.get());
    List<String> sqls = SqlReader.readSqls(
        "io/github/kylinhunter/commons/jdbc/execute/execute.sql");

    sqlExecutor.executeBatch(sqls, false);

    sqlExecutor.executeBatch(sqls, true);

    Connection connection = sqlExecutor.getConnection();
    Assertions.assertFalse(connection.isClosed());
    connection.close();

    List<Object[]> objs = sqlExecutor.query(sqls.get(0), new ArrayListHandler());
    for (Object[] os : objs) { // object[]中保存了object对象
      for (Object o : os) {
        System.out.print(o + "\t");
      }
      System.out.println();
    }
  }


}
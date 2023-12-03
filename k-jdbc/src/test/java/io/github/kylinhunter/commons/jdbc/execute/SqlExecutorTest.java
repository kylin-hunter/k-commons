package io.github.kylinhunter.commons.jdbc.execute;

import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import java.util.List;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.jupiter.api.Test;

class SqlExecutorTest {

  @Test
  void execute() {
    DataSourceManager dataSourceManager = new DataSourceManager();
    SqlExecutor sqlExecutor = new SqlExecutor(dataSourceManager.getDefaultDataSource());
    List<String> sqls = SqlFileReader.read(
        "io/github/kylinhunter/commons/jdbc/execute/execute.sql");

    sqlExecutor.execute(sqls, false);

    List<Object[]> objs = sqlExecutor.query(sqls.get(0), new ArrayListHandler());
    for (Object[] os : objs) { // object[]中保存了object对象
      for (Object o : os) {
        System.out.print(o + "\t");
      }
      System.out.println();
    }
  }
}
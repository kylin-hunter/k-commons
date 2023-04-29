package io.github.kylinhunter.commons.jdbc.datasource;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataSourceUtilsTest {

  @Test
  void test() throws SQLException {
    DataSource defaultDataSource = DataSourceUtils.getDefaultDataSource();

    QueryRunner runner = new QueryRunner(defaultDataSource);

    // 2.执行查询
    List<Object[]> objs = runner.query(" desc test_user", new ArrayListHandler());
    for (Object[] os : objs) { // object[]中保存了object对象
      for (Object o : os) {
        System.out.print(o + "\t");
      }
      System.out.println();
    }
    Assertions.assertTrue(objs.size() > 0);
  }
}

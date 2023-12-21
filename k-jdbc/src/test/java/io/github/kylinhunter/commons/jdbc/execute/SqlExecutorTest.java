package io.github.kylinhunter.commons.jdbc.execute;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SqlExecutorTest {

  @SuppressWarnings("unchecked")
  @Test
  void execute() throws SQLException {
    DataSource dataSource = TestHelper.mockDataSource();
    QueryRunner runner = Mockito.mock(QueryRunner.class);
    Mockito.when(runner.query(anyString(), any(ResultSetHandler.class), eq(new Object[]{})))
        .thenReturn(ListUtils.newArrayList(
            new Object[]{1, 2}, new Object[]{2, 3}
        ));
    SqlExecutor sqlExecutor = new SqlExecutor(dataSource);
    ReflectUtils.setField(sqlExecutor, "queryRunner", runner);

    List<String> sqls = SqlReader.readSqls(
        "io/github/kylinhunter/commons/jdbc/execute/execute.sql");

    sqlExecutor.executeBatch(false, sqls);

    sqlExecutor.executeBatch(true, sqls);

    Connection connection = sqlExecutor.getConnection();
    Assertions.assertFalse(connection.isClosed());
    connection.close();

    List<Object[]> objs = sqlExecutor.query(sqls.get(0), new ArrayListHandler());
    Mockito.verify(runner, Mockito.times(1))
        .query(anyString(), any(ResultSetHandler.class),
            eq(new Object[]{}));
    for (Object[] os : objs) { // object[]中保存了object对象
      for (Object o : os) {
        System.out.print(o + "\t");
      }
      System.out.println();
    }
    Assertions.assertEquals(2, objs.size());
  }


}
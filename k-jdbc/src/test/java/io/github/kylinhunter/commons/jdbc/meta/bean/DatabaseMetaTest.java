package io.github.kylinhunter.commons.jdbc.meta.bean;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatabaseMetaTest {

  @Test
  void calDbType() {

    String jdbcUrl = TestHelper.MYSQL_JDBC_URL;
    DbType dbType = JdbcUtils.calDbType(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, dbType);

    jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORACLE";
    dbType = JdbcUtils.calDbType(jdbcUrl);
    Assertions.assertEquals(DbType.ORACLE, dbType);

    jdbcUrl = "jdbc:sqlserver://localhost:1433; DatabaseName=test";
    dbType = JdbcUtils.calDbType(jdbcUrl);

    Assertions.assertEquals(DbType.SQL_SERVER, dbType);
  }
}

package io.github.kylinhunter.commons.jdbc.meta.bean;

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatabaseMetaTest {

  @Test
  void calDbType() {
    DatabaseMeta databaseMeta = new DatabaseMeta();

    String jdbcUrl =
        "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    DbType dbType = databaseMeta.calDbType(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, dbType);

    jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORACLE";
    dbType = databaseMeta.calDbType(jdbcUrl);
    Assertions.assertEquals(DbType.ORACLE, dbType);

    jdbcUrl = "jdbc:sqlserver://localhost:1433; DatabaseName=test";
    dbType = databaseMeta.calDbType(jdbcUrl);

    Assertions.assertEquals(DbType.SQL_SERVER, dbType);
  }
}

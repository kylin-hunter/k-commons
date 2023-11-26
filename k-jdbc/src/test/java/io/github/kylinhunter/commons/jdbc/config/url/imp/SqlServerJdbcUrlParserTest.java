package io.github.kylinhunter.commons.jdbc.config.url.imp;

import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SqlServerJdbcUrlParserTest {

  @Test
  void parse() {
    String jdbcUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=test;username=sa; password=passwd";
    JdbcUrlParser jdbcUrlParser = new SqlServerJdbcUrlParser();
    JdbcUrl jdbcUrlInfo = jdbcUrlParser.parse(jdbcUrl);
    Assertions.assertEquals(DbType.SQL_SERVER, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(1433, jdbcUrlInfo.getPort());
    Assertions.assertEquals("test", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(3, jdbcUrlInfo.getParams().size());

    jdbcUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=test";
    jdbcUrlInfo = jdbcUrlParser.parse(jdbcUrl);
    Assertions.assertEquals(DbType.SQL_SERVER, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(1433, jdbcUrlInfo.getPort());
    Assertions.assertEquals("test", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(1, jdbcUrlInfo.getParams().size());
  }


  @Test
  void parse2() {
    String jdbcUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=test;username=sa; password=passwd";
    JdbcUrl jdbcUrlInfo = JdbcUtils.parse(jdbcUrl);
    Assertions.assertEquals(DbType.SQL_SERVER, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(1433, jdbcUrlInfo.getPort());
    Assertions.assertEquals("test", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(3, jdbcUrlInfo.getParams().size());

    jdbcUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=test";
    jdbcUrlInfo = JdbcUtils.parse(jdbcUrl);
    Assertions.assertEquals(DbType.SQL_SERVER, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(1433, jdbcUrlInfo.getPort());
    Assertions.assertEquals("test", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(1, jdbcUrlInfo.getParams().size());
  }
}
package io.github.kylinhunter.commons.jdbc.config.url.imp;

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.url.imp.OracleJdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OracleJdbcUrlParserTest {

  @Test
  void parse() {
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORACLE?user=your_username&password=your_password";
    JdbcUrlParser jdbcUrlParser = new OracleJdbcUrlParser();
    JdbcUrl jdbcUrlInfo = jdbcUrlParser.parse(jdbcUrl);
    Assertions.assertEquals(DbType.ORACLE, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(1521, jdbcUrlInfo.getPort());
    Assertions.assertEquals("ORACLE", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(2, jdbcUrlInfo.getParams().size());
    Assertions.assertEquals(jdbcUrl, jdbcUrlParser.toString(jdbcUrlInfo));

    jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORACLE";
    jdbcUrlInfo = jdbcUrlParser.parse(jdbcUrl);
    Assertions.assertEquals(DbType.ORACLE, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(1521, jdbcUrlInfo.getPort());
    Assertions.assertEquals("ORACLE", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(0, jdbcUrlInfo.getParams().size());
    Assertions.assertEquals(jdbcUrl, jdbcUrlParser.toString(jdbcUrlInfo));

  }


  @Test
  void parse2() {
    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORACLE?user=your_username&password=your_password";
    JdbcUrl jdbcUrlInfo = JdbcUtils.parse(jdbcUrl);
    Assertions.assertEquals(DbType.ORACLE, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(1521, jdbcUrlInfo.getPort());
    Assertions.assertEquals("ORACLE", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(2, jdbcUrlInfo.getParams().size());
    Assertions.assertEquals(jdbcUrl, JdbcUtils.toString(jdbcUrlInfo));

    jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORACLE";
    jdbcUrlInfo = JdbcUtils.parse(jdbcUrl);
    Assertions.assertEquals(DbType.ORACLE, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(1521, jdbcUrlInfo.getPort());
    Assertions.assertEquals("ORACLE", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(0, jdbcUrlInfo.getParams().size());
    Assertions.assertEquals(jdbcUrl, JdbcUtils.toString(jdbcUrlInfo));

  }
}
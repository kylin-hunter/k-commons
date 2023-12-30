package io.github.kylinhunter.commons.jdbc.config.url.imp;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.url.imp.MysqlJdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MysqlJdbcUrlParserTest {

  @Test
  void parse() {
    String jdbcUrl = TestHelper.MYSQL_JDBC_URL;
    JdbcUrlParser jdbcUrlParser = new MysqlJdbcUrlParser();
    JdbcUrl jdbcUrlInfo = jdbcUrlParser.parse(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(3306, jdbcUrlInfo.getPort());
    Assertions.assertEquals("kp", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(6, jdbcUrlInfo.getParams().size());

    Assertions.assertEquals(jdbcUrl, jdbcUrlParser.toString(jdbcUrlInfo));

    jdbcUrl = "jdbc:mysql://localhost:3306/kp";
    jdbcUrlInfo = jdbcUrlParser.parse(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(3306, jdbcUrlInfo.getPort());
    Assertions.assertEquals("kp", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(0, jdbcUrlInfo.getParams().size());
    Assertions.assertEquals(jdbcUrl, jdbcUrlParser.toString(jdbcUrlInfo));
  }

  @Test
  void parse2() {
    String jdbcUrl = TestHelper.MYSQL_JDBC_URL;
    JdbcUrl jdbcUrlInfo = JdbcUtils.parse(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(3306, jdbcUrlInfo.getPort());
    Assertions.assertEquals("kp", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(6, jdbcUrlInfo.getParams().size());
    Assertions.assertEquals(jdbcUrl, JdbcUtils.toString(jdbcUrlInfo));

    jdbcUrl = "jdbc:mysql://localhost:3306/kp";
    jdbcUrlInfo = JdbcUtils.parse(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(3306, jdbcUrlInfo.getPort());
    Assertions.assertEquals("kp", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(0, jdbcUrlInfo.getParams().size());
    Assertions.assertEquals(jdbcUrl, JdbcUtils.toString(jdbcUrlInfo));

  }


}
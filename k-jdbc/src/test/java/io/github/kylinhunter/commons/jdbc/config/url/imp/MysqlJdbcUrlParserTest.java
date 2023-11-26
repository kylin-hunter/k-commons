package io.github.kylinhunter.commons.jdbc.config.url.imp;

import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MysqlJdbcUrlParserTest {

  @Test
  void parse() {
    String jdbcUrl = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    JdbcUrlParser jdbcUrlParser = new MysqlJdbcUrlParser();
    JdbcUrl jdbcUrlInfo = jdbcUrlParser.parse(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(3306, jdbcUrlInfo.getPort());
    Assertions.assertEquals("kp", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(6, jdbcUrlInfo.getParams().size());

    jdbcUrl = "jdbc:mysql://localhost:3306/kp";
    jdbcUrlInfo = jdbcUrlParser.parse(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(3306, jdbcUrlInfo.getPort());
    Assertions.assertEquals("kp", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(0, jdbcUrlInfo.getParams().size());
  }

  @Test
  void parse2() {
    String jdbcUrl = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    JdbcUrl jdbcUrlInfo = JdbcUtils.parse(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(3306, jdbcUrlInfo.getPort());
    Assertions.assertEquals("kp", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(6, jdbcUrlInfo.getParams().size());

    jdbcUrl = "jdbc:mysql://localhost:3306/kp";
    jdbcUrlInfo = JdbcUtils.parse(jdbcUrl);
    Assertions.assertEquals(DbType.MYSQL, jdbcUrlInfo.getDbType());
    Assertions.assertEquals("localhost", jdbcUrlInfo.getHost());
    Assertions.assertEquals(3306, jdbcUrlInfo.getPort());
    Assertions.assertEquals("kp", jdbcUrlInfo.getDatabase());
    Assertions.assertEquals(0, jdbcUrlInfo.getParams().size());
  }


}
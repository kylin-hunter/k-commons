package io.github.kylinhunter.commons.jdbc.config.url;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JdbcUrlTest {

  @Test
  void test() {

    String jdbcUrlStr = TestHelper.MYSQL_JDBC_URL;

    JdbcUrl jdbcUrl = JdbcUtils.parse(jdbcUrlStr);
    Assertions.assertNotNull(jdbcUrl);

  }
}
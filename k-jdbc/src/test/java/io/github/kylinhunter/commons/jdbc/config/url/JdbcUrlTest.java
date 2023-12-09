package io.github.kylinhunter.commons.jdbc.config.url;

import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JdbcUrlTest {

  @Test
  void test() {

    String jdbcUrlStr = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";

    JdbcUrl jdbcUrl = JdbcUtils.parse(jdbcUrlStr);
    Assertions.assertNotNull(jdbcUrl);

  }
}
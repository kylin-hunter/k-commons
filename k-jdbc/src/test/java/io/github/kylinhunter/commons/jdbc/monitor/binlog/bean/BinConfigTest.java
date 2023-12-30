package io.github.kylinhunter.commons.jdbc.monitor.binlog.bean;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinConfig;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-17 14:48
 */
public class BinConfigTest {

  @Test
  public void test1() {
    BinConfig binConfig = new BinConfig();

    binConfig.setPort(999);
    Assertions.assertEquals(999, binConfig.getPort());

    binConfig.setServerId(100);
    Assertions.assertEquals(100, binConfig.getServerId());

    binConfig.setUrl(TestHelper.MYSQL_JDBC_URL);
    Assertions.assertEquals("localhost", binConfig.getHostname());
    Assertions.assertEquals(3306, binConfig.getPort());

    binConfig.setJdbcUrl(JdbcUtils.parse(TestHelper.MYSQL_JDBC_URL));
    Assertions.assertEquals(3306, binConfig.getPort());


  }

  @Test
  public void test2() {
    BinConfig binConfig = Mockito.mock(BinConfig.class);
    binConfig.setPort(999);
    Assertions.assertEquals(0, binConfig.getPort());
    binConfig.setServerId(100);
    Assertions.assertEquals(0, binConfig.getServerId());
  }

  @Test
  public void test3() {
    BinConfig binConfig = Mockito.spy(BinConfig.class);
    binConfig.setPort(999);
    Assertions.assertEquals(999, binConfig.getPort());

    Mockito.when(binConfig.getServerId()).thenReturn(100L);
    Assertions.assertEquals(100, binConfig.getServerId());
  }


}
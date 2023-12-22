package io.github.kylinhunter.commons.jdbc.monitor.binlog.bean;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinLogConfig;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-17 14:48
 */
public class BinLogConfigTest {

  @Test
  public void test1() {
    BinLogConfig binLogConfig = new BinLogConfig();

    binLogConfig.setPort(999);
    Assertions.assertEquals(999, binLogConfig.getPort());

    binLogConfig.setServerId(100);
    Assertions.assertEquals(100, binLogConfig.getServerId());

    binLogConfig.setUrl(TestHelper.MYSQL_JDBC_URL);
    Assertions.assertEquals("localhost", binLogConfig.getHostname());
    Assertions.assertEquals(3306, binLogConfig.getPort());

    binLogConfig.setJdbcUrl(JdbcUtils.parse(TestHelper.MYSQL_JDBC_URL));
    Assertions.assertEquals(3306, binLogConfig.getPort());


  }

  @Test
  public void test2() {
    BinLogConfig binLogConfig = Mockito.mock(BinLogConfig.class);
    binLogConfig.setPort(999);
    Assertions.assertEquals(0, binLogConfig.getPort());
    binLogConfig.setServerId(100);
    Assertions.assertEquals(0, binLogConfig.getServerId());
  }

  @Test
  public void test3() {
    BinLogConfig binLogConfig = Mockito.spy(BinLogConfig.class);
    binLogConfig.setPort(999);
    Assertions.assertEquals(999, binLogConfig.getPort());

    Mockito.when(binLogConfig.getServerId()).thenReturn(100L);
    Assertions.assertEquals(100, binLogConfig.getServerId());
  }


}
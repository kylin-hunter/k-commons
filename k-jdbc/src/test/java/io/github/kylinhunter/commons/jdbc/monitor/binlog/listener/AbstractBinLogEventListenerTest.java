package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

class AbstractBinLogEventListenerTest {

  @Test
  void test() {
    AbstractBinLogEventListener envenListener = new AbstractBinLogEventListener() {


      @Override
      public void init(DataSource dataSource) {

      }
    };

    String sql = "alter table aaa add colum";

//    Assertions.assertEquals("aaa", envenListener.getAlterTableName(sql));
  }
}
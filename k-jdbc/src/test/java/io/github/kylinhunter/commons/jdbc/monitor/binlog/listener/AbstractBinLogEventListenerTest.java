package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractBinLogEventListenerTest {

  @Test
  void test() {
    AbstractBinLogEventListener envenListener = new AbstractBinLogEventListener() {

      @Override
      protected void insertDataRecord(TableId tableKey, WriteRowsEventData writeRowsEventData) {

      }

      @Override
      protected void deleteDataRecord(TableId tableKey, DeleteRowsEventData eventData) {

      }

      @Override
      protected void updateDataRecord(TableId tableKey, UpdateRowsEventData eventData) {

      }

      @Override
      public void init(DataSource dataSource) {

      }
    };

    String sql = "alter table aaa add colum";

    Assertions.assertEquals("aaa", envenListener.getAlterTableName(sql));
  }
}
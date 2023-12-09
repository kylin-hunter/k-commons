package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractBinLogEventListenerTest {

  @Test
  void test() {
    AbstractBinLogEventListener envenListener = new AbstractBinLogEventListener() {

      @Override
      protected void insertDataRecord(String tableName, WriteRowsEventData writeRowsEventData,
          TableMeta tableMeta, List<ColumnMeta> columnMetas) {

      }

      @Override
      protected void deleteDataRecord(String tableName, DeleteRowsEventData eventData,
          TableMeta tableMeta, List<ColumnMeta> columnMetas) {

      }

      @Override
      protected void updateDataRecord(String tableName, UpdateRowsEventData eventData,
          TableMeta tableMeta, List<ColumnMeta> columnMetas) {

      }

      @Override
      public void init(DataSource dataSource) {
        
      }
    };

    String sql = "alter table aaa add colum";

    Assertions.assertEquals("aaa", envenListener.getAlterTableName(sql));
  }
}
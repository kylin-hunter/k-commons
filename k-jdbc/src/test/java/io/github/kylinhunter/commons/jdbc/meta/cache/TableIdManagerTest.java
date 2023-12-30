package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TableIdManagerTest {

  @Test
  void test() throws SQLException {
    DataSource dataSource = TestHelper.mockDataSource();
    TableIdManager tableIdManager = new TableIdManager(dataSource);
    tableIdManager.update(1L, TestHelper.DATABASE, TestHelper.TEST_TABLE_ROLE1);
    TableMeta tableMeta = tableIdManager.getTableMeta(1L);
    Assertions.assertEquals(TestHelper.DATABASE, tableMeta.getDatabase());
    ColumnMetas columnMetas = tableIdManager.getColumnMetas(1L);
    Assertions.assertNotNull(columnMetas);


  }

}
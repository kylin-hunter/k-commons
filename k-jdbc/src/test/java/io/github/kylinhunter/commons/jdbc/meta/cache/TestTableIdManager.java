package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TableIdManager;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;

public class TestTableIdManager {

  public static void main(String[] args) {

    DataSourceManager dataSourceManager = new DataSourceManager(true);
    DataSource dataSource = dataSourceManager.get();
    TableIdManager cache = new TableIdManager(dataSource);
    cache.update(1L, TestHelper.DATABASE, TestHelper.TEST_TABLE_ROLE1);
    TableMeta tableMeta = cache.getTableMeta(1L);
    Assertions.assertEquals(TestHelper.DATABASE, tableMeta.getDatabase());
    ColumnMetas columnMetas = cache.getColumnMetas(1L);
    Assertions.assertNotNull(columnMetas);

    dataSourceManager.close();
  }

}
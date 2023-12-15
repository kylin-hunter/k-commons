package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatabaseMetaCacheTest {

  @Test
  void test() {

    DataSourceManager dataSourceManager = new DataSourceManager(true);
    DataSource dataSource = dataSourceManager.get();
    DatabaseMetaCache cache = new DatabaseMetaCache(dataSource);
    cache.updateTableCache(1L, TestHelper.DATABASE, TestHelper.TEST_TABLE_ROLE, true);
    TableMeta tableMeta = cache.getTableMeta(1L);
    Assertions.assertEquals(TestHelper.DATABASE, tableMeta.getDatabase());
    ColumnMetas columnMetas = cache.getColumnMetas(1L);
    Assertions.assertNotNull(columnMetas);
    ColumnMeta columnMeta = cache.getPkColumnMeta(1L, TestHelper.DATABASE,
        TestHelper.TEST_TABLE_ROLE, "id");
    Assertions.assertNotNull(columnMeta);

    dataSourceManager.close();
  }

}
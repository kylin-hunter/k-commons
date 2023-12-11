package io.github.kylinhunter.commons.jdbc.meta.column;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.MysqlColumnReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColumnReaderTest {

  @Test
  void test() {

    ColumnReader columnReader = new MysqlColumnReader();

    ColumnMetas columnMetas = columnReader.getColumnMetaData("", TestHelper.TEST_TABLE);
    if (columnMetas.size() != 19) {
      TestHelper.initTestSQl();
      columnMetas = columnReader.getColumnMetaData("", TestHelper.TEST_TABLE);
    }
    Assertions.assertEquals(16, columnMetas.size());
    for (ColumnMeta columnMeta : columnMetas.getColumns()) {
      System.out.println("################" + columnMeta.getColumnName() + "###############");
      System.out.println(columnMeta);
      Assertions.assertNotNull(columnMeta.getJavaClass());
      System.out.println(columnMeta.getColumnName() + ":" + columnMeta.getJavaClass().getName());
      columnMeta.getRawMetadatas().forEach((k, v) -> System.out.println(k + ":" + v));
    }
  }
}

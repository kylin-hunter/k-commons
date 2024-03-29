package io.github.kylinhunter.commons.jdbc.meta.column;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.MysqlColumnReader;
import org.junit.jupiter.api.Assertions;

public class TestColumnReader {

  public static void main(String[] args) {

    ColumnReader columnReader = new MysqlColumnReader();

    ColumnMetas columnMetas = columnReader.getColumnMetaData("", TestHelper.TEST_TABLE_ROLE1);
    if (columnMetas.size() == 0) {
      TestHelper.initTestSQl();
      columnMetas = columnReader.getColumnMetaData("", TestHelper.TEST_TABLE_ROLE1);
    }
    for (ColumnMeta columnMeta : columnMetas.getColumns()) {
      System.out.println("################" + columnMeta.getColumnName() + "###############");
      System.out.println(columnMeta);
      Assertions.assertNotNull(columnMeta.getJavaClass());
      System.out.println(columnMeta.getColumnName() + ":" + columnMeta.getJavaClass().getName());
      columnMeta.getRawMetadatas().forEach((k, v) -> System.out.println(k + ":" + v));
    }
    Assertions.assertEquals(16, columnMetas.size());

    ColumnMeta columnMeta = columnMetas.getByIndex(0);
    Assertions.assertEquals("id", columnMeta.getColumnName());
  }
}

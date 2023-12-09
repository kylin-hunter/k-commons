package io.github.kylinhunter.commons.jdbc.meta.column;

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.MetaReaderFactory;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReaderTest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColumnReaderTest {

  @Test
  void test() {

    ColumnReader columnReader = MetaReaderFactory.getColumnMetaReader(DbType.MYSQL);

    List<ColumnMeta> columnMetas = columnReader.getColumnMetaData("", "k_junit_jdbc_role");
    if (columnMetas.size() != 19) {
      TableReaderTest.initTestSQl();
      columnMetas = columnReader.getColumnMetaData("", "k_junit_jdbc_role");
    }
//    Assertions.assertEquals(19, columnMetas.size());
    for (ColumnMeta columnMeta : columnMetas) {
      System.out.println("################" + columnMeta.getColumnName() + "###############");
      System.out.println(columnMeta);
      Assertions.assertNotNull(columnMeta.getJavaClass());
      System.out.println(columnMeta.getColumnName() + ":" + columnMeta.getJavaClass().getName());
      columnMeta.getRawMetadatas().forEach((k, v) -> System.out.println(k + ":" + v));
    }
  }
}

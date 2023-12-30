package io.github.kylinhunter.commons.jdbc.meta.column;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.MysqlColumnReader;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColumnReaderTest {

  @Test
  void test() throws SQLException {

    DataSource dataSource = TestHelper.mockDataSource();

    ColumnReader columnReader = new MysqlColumnReader(dataSource);

    ColumnMetas columnMetas = columnReader.getColumnMetaData("", TestHelper.TEST_TABLE_ROLE1);
    Assertions.assertEquals(1, columnMetas.size());
    for (ColumnMeta columnMeta : columnMetas.getColumns()) {
      System.out.println("################" + columnMeta.getColumnName() + "###############");
      System.out.println(columnMeta);
      Assertions.assertNotNull(columnMeta.getJavaClass());
      System.out.println(columnMeta.getColumnName() + "/" + columnMeta.getJavaClass().getName());
      columnMeta.getRawMetadatas().forEach((k, v) -> System.out.println(k + ":" + v));
    }
    Assertions.assertEquals(1, columnMetas.size());

    ColumnMeta columnMeta = columnMetas.getByIndex(0);
    Assertions.assertEquals("id", columnMeta.getColumnName());
  }
}

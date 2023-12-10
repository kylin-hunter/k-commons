package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatabaseMetaReaderTest {

  @Test
  void test() {
    DatabaseMetaReader databaseMetaReader = new DatabaseMetaReader(true);
    DatabaseMeta databaseMeta = databaseMetaReader.getMetaData();
    System.out.println(databaseMeta);
    Assertions.assertEquals(DbType.MYSQL, databaseMeta.getDbType());
  }
}

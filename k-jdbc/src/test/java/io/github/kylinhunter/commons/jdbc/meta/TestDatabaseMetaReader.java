package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import org.junit.jupiter.api.Assertions;

class TestDatabaseMetaReader {

  public static void main(String[] args) {

    DatabaseMetaReader databaseMetaReader = CF.get(DatabaseMetaReader.class);
    DatabaseMeta databaseMeta = databaseMetaReader.getMetaData();
    System.out.println(databaseMeta);
    Assertions.assertEquals(DbType.MYSQL, databaseMeta.getDbType());
  }
}

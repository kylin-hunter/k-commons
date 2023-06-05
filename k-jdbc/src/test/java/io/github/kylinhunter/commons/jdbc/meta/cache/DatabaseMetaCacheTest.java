package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import org.junit.jupiter.api.Test;

class DatabaseMetaCacheTest {

  @Test
  void load() {
    DatabaseMetaCache databaseMetaCache = CF.get(DatabaseMetaCache.class);
    DatabaseMeta databaseMeta0 = databaseMetaCache.getByDsNo(0);
    DatabaseMeta databaseMeta1 = databaseMetaCache.getByDsNo(1);
    DatabaseMeta databaseMeta2 = databaseMetaCache.getByDsNo(2);

    System.out.println(databaseMeta0);
    System.out.println(databaseMeta1);
    System.out.println(databaseMeta2);

    databaseMeta0 = databaseMetaCache.getByDsName("datasource-0");
    databaseMeta1 = databaseMetaCache.getByDsName("datasource-1");
    databaseMeta2 = databaseMetaCache.getByDsName("2");

    System.out.println(databaseMeta0);
    System.out.println(databaseMeta1);
    System.out.println(databaseMeta2);
  }
}

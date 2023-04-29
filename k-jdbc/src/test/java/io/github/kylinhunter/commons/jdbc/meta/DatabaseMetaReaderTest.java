package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatabaseMetaReaderTest {

    @Test
    void test() {
        DatabaseMetaReader databaseMetaReader = CF.get(DatabaseMetaReader.class);
        DatabaseMeta databaseMeta = databaseMetaReader.getDatabaseMetaData();
        System.out.println(databaseMeta);
        Assertions.assertEquals(DbType.MYSQL, databaseMeta.getDbType());
    }
}
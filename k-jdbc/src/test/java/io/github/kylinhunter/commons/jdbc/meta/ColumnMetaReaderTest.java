package io.github.kylinhunter.commons.jdbc.meta;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;

class ColumnMetaReaderTest {

    @Test
    void test() {
        ColumnMetaReader columnMetaReader = CF.get(ColumnMetaReader.class);

        List<ColumnMeta> columnMetas = columnMetaReader.getColumnMetaData("", "test_user");
        Assertions.assertTrue(!columnMetas.isEmpty());
        for (ColumnMeta columnMeta : columnMetas) {
            System.out.println(columnMeta);
            Assertions.assertNotNull(columnMeta.getJavaClass());
            System.out.println(columnMeta.getColumnName() + ":" + columnMeta.getJavaClass().getName());
        }


    }
}
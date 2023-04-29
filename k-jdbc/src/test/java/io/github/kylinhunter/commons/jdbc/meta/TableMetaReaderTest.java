package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TableMetaReaderTest {

    @Test
    void test() {
        TableMetaReader tableMetaReader = CF.get(TableMetaReader.class);

        List<TableMeta> tableMetas = tableMetaReader.getTableMetaDatas("", "test_user");
        Assertions.assertTrue(!tableMetas.isEmpty());
        for (TableMeta tableMeta : tableMetas) {
            System.out.println(tableMeta);
        }

    }
}
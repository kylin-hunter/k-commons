package io.github.kylinhunter.commons.jdbc.meta.table;

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.MetaReaderFactory;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TableReaderTest {

  @Test
  void test() {
    TableReader tableReader = MetaReaderFactory.getTableMetaReader(DbType.MYSQL);

    List<TableMeta> tableMetas = tableReader.getTableMetaDatas("", "k_jdbc_test_user");
    Assertions.assertEquals(1, tableMetas.size());
    TableMeta tableMeta = tableMetas.get(0);
    System.out.println(tableMeta);
    Map<String, Object> rawMetadatas = tableMeta.getRawMetadatas();
    rawMetadatas.forEach((k, v) -> System.out.println(k + ":" + v));

  }
}
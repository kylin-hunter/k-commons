package io.github.kylinhunter.commons.jdbc.meta.table;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

public class TestTableReader {

  public static void main(String[] args) {
    TableReader tableReader = new MysqlTableReader();

    List<TableMeta> tableMetas = tableReader.getTableMetaDatas("", TestHelper.TEST_TABLE_ROLE1);
    if (tableMetas.size() == 0) {
      TestHelper.initTestSQl();
      tableMetas = tableReader.getTableMetaDatas("", TestHelper.TEST_TABLE_ROLE1);
    }
    Assertions.assertEquals(1, tableMetas.size());
    TableMeta tableMeta = tableMetas.get(0);
    System.out.println(tableMeta);
    Map<String, Object> rawMetadatas = tableMeta.getRawMetadatas();
    rawMetadatas.forEach((k, v) -> System.out.println(k + ":" + v));

    Assertions.assertTrue(tableMeta.equals(TestHelper.DATABASE, TestHelper.TEST_TABLE_ROLE1));
  }


}

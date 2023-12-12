package io.github.kylinhunter.commons.jdbc.dao;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import org.junit.jupiter.api.Test;

class BasicDAOTest {

  @Test
  void ensureTableExists() {
    BasicDAO basicDAO = new BasicDAO(null, true);
    String tableName = TestHelper.TEST_TABLE_TMP;
    basicDAO.dropTable(tableName);
    basicDAO.ensureTableExists(tableName, "create table " + tableName + " (id int)");

  }
}
package io.github.kylinhunter.commons.jdbc.dao;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import org.junit.jupiter.api.Test;

class AbsctractBasicDAOTest {

  @Test
  void ensureTableExists() {
    AbsctractBasicDAO basicDAO = new AbsctractBasicDAO(null, true);
    String tableName = TestHelper.TEST_TABLE_TMP;
    basicDAO.dropTable(tableName);
    basicDAO.ensureTableExists(tableName, "create table " + tableName + " (id int)");

  }
}
package io.github.kylinhunter.commons.jdbc.scan;

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReaderTest;
import org.junit.jupiter.api.Test;

class TableScanManagerTest {

  @Test
  void init() {
    TableReaderTest.initTestSQl();
    TableScanManager manager = new TableScanManager(DbType.MYSQL);
    manager.init();
    ScanOption scanOption = new ScanOption();
    scanOption.setScanLimit(2);
    manager.scan("k_jdbc_test_role", scanOption);
  }
}
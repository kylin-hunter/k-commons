package io.github.kylinhunter.commons.jdbc.scan;

import io.github.kylinhunter.commons.jdbc.meta.table.TableReaderTest;

class TestTableScanManager {

  public static void main(String[] args) {

    TableReaderTest.initTestSQl();
    TableScanManager manager = new TableScanManager();
    manager.init();
    TableScan tableScan = new TableScan();
    tableScan.setTableName("k_jdbc_test_role");
    tableScan.setTableIdColName("id");
    tableScan.setTableTimeColName("sys_auto_updated");
    tableScan.setScanLimit(1);
    tableScan.setScanInterval(1000);
    tableScan.setScanSameTimeInterval(100);
    manager.scan(tableScan);

  }
}
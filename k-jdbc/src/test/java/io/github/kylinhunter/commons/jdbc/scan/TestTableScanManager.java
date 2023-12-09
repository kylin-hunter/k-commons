package io.github.kylinhunter.commons.jdbc.scan;

import io.github.kylinhunter.commons.jdbc.meta.table.TableReaderTest;

class TestTableScanManager {

  public static void main(String[] args) {

    TableReaderTest.initTestSQl();
    TableScanManager manager = new TableScanManager();

    TableScan tableScan = new TableScan();
    tableScan.setTableName("k_junit_jdbc_role");
    tableScan.setTableIdColName("id");
    tableScan.setTableTimeColName("sys_auto_updated");
    tableScan.setSaveDestination("k_junit_scan_processor");
    tableScan.setScanLimit(1);
    tableScan.setScanInterval(1000);
    tableScan.setScanSameTimeInterval(100);
    manager.init(tableScan);
    manager.scan(tableScan);
    manager.shutdown();

  }
}
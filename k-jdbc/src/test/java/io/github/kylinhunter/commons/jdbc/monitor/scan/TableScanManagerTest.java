package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.meta.table.TableReaderTest;
import org.junit.jupiter.api.Test;

class TableScanManagerTest {

  @Test
  void init() {
    TableReaderTest.initTestSQl();
    TableScanManager manager = new TableScanManager();

    TableScan tableScan = new TableScan();
    tableScan.setTableName("k_junit_jdbc_role");
    tableScan.setTableIdColName("id");
    tableScan.setTableTimeColName("sys_auto_updated");
    tableScan.setSaveDestination("k_junit_table_monitor_scan_task");
    tableScan.setScanLimit(7);
    tableScan.setScanInterval(1000);
    tableScan.setScanSameTimeInterval(100);
    tableScan.setDaemon(false);
    manager.init(tableScan);
    manager.clean(tableScan);
    manager.scan(tableScan);


  }
}
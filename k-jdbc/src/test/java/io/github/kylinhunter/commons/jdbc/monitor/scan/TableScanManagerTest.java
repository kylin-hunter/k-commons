package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import org.junit.jupiter.api.Test;

class TableScanManagerTest {

  @Test
  void init() {
    TestHelper.initTestSQl();
    TableScanManager manager = new TableScanManager();

    TableScan tableScan = new TableScan();
    tableScan.setTableName(TestHelper.TEST_TABLE);
    tableScan.setTableIdColName("id");
    tableScan.setTableTimeColName("sys_auto_updated");
    tableScan.setDestination(TestHelper.MONITOR_SCAN_TASK);
    tableScan.setScanLimit(100);
    tableScan.setScanInterval(-1);
    tableScan.setScanSameTimeInterval(100);
    manager.init(tableScan);
    manager.clean(tableScan);
    manager.scan(tableScan);


  }
}
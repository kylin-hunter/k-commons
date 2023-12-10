package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.meta.table.TableReaderTest;
import io.github.kylinhunter.commons.util.ThreadHelper;
import java.util.concurrent.TimeUnit;

class TestTableScanManager {

  public static void main(String[] args) {

    TableReaderTest.initTestSQl();
    TableScanManager manager = new TableScanManager(true);

    TableScan tableScan = new TableScan();
    tableScan.setTableName("k_junit_jdbc_role");
    tableScan.setTableIdColName("id");
    tableScan.setTableTimeColName("sys_auto_updated");
    tableScan.setScanLimit(1);
    tableScan.setScanInterval(1000);
    tableScan.setScanSameTimeInterval(100);
    manager.init(tableScan);
    manager.scan(tableScan);
    ThreadHelper.sleep(100, TimeUnit.MINUTES);
    manager.shutdown();

  }
}
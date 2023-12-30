package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.TableScanConfig;
import io.github.kylinhunter.commons.jdbc.monitor.task.TestScanRowListener;
import javax.sql.DataSource;

class TestScanTableMonitor {

  public static void main(String[] args) {

    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);

    TableScanConfig tablescanConfig = getTablescanConfig();
    TableMonitor tableMonitor = new ScanTableMonitor(dataSource, tablescanConfig);
    tableMonitor.reset();

    TestScanRowListener testRowListener = new TestScanRowListener();
    tableMonitor.setRowListener(testRowListener);
    tableMonitor.start();

  }

  public static TableScanConfig getTablescanConfig() {
    TableScanConfig tableScanConfig = new TableScanConfig();
    tableScanConfig.setThreadPoolSize(1);

    ScanTable scanTable1 = getScanTable();
    scanTable1.setTableName(TestHelper.TEST_TABLE_ROLE1);
    tableScanConfig.add(scanTable1);

    ScanTable scanTable2 = getScanTable();
    scanTable2.setTableName(TestHelper.TEST_TABLE_ROLE2);
    tableScanConfig.add(scanTable2);
    tableScanConfig.setMaxRetryTimes(1);
    return tableScanConfig;
  }

  public static ScanTable getScanTable() {
    ScanTable scanTable = new ScanTable();
    scanTable.setTableName(TestHelper.TEST_TABLE_ROLE2);
    scanTable.setDatabase(TestHelper.DATABASE);
    scanTable.setDestination(TestHelper.TEST_SCAN_TASK);
    scanTable.setPkColName("id");
    scanTable.setDelColName("sys_delete_flag");
    scanTable.setTableTimeName("sys_auto_updated");
    scanTable.setScanLimit(1);
    scanTable.setScanInterval(100);

    return scanTable;
  }
}
package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

class ScanTableMonitorTest {

  @Test
  void init() {
    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);

    TableScanConfig tableScanConfig = new TableScanConfig();
    tableScanConfig.setServerId("1");
    tableScanConfig.setScheduleCorePoolSize(1);

    ScanTable scanTable1 = TestScanTableMonitor.getScanTable();
    scanTable1.setTableName(TestHelper.TEST_TABLE_ROLE1);
    scanTable1.setDestination(TestHelper.MONITOR_SCAN_TASK);
    scanTable1.setScanInterval(-1);
    tableScanConfig.add(scanTable1);

    ScanTable scanTable2 = TestScanTableMonitor.getScanTable();
    scanTable2.setTableName(TestHelper.TEST_TABLE_ROLE2);
    scanTable1.setDestination(TestHelper.MONITOR_SCAN_TASK);
    scanTable1.setScanInterval(-1);
    tableScanConfig.add(scanTable2);
    ScanTableMonitor manager = new ScanTableMonitor(dataSource, tableScanConfig);

    manager.reset();
    manager.start();

  }
}
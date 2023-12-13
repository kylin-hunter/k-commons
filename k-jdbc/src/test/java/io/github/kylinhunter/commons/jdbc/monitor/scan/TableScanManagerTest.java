package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

class TableScanManagerTest {

  @Test
  void init() {
    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);
    TableScanManager manager = new TableScanManager(dataSource);

    TableScanConfig tableScanConfig = new TableScanConfig();
    tableScanConfig.setTableName(TestHelper.TEST_TABLE_ROLE);
    tableScanConfig.setTableIdColName("id");
    tableScanConfig.setTableTimeColName("sys_auto_updated");
    tableScanConfig.setDestination(TestHelper.MONITOR_SCAN_TASK);
    tableScanConfig.setScanLimit(100);
    tableScanConfig.setScanInterval(-1);
    tableScanConfig.setScanSameTimeInterval(100);
    manager.init(tableScanConfig);
    manager.clean(tableScanConfig);
    manager.scan(tableScanConfig);


  }
}
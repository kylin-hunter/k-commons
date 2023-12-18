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
    tableScanConfig.setServerId("1");
    tableScanConfig.setScheduleCorePoolSize(1);

    ScanTable scanTable1 = TestTableScanManager.getScanTable();
    scanTable1.setTableName(TestHelper.TEST_TABLE_ROLE1);
    scanTable1.setScanInterval(-1);
    tableScanConfig.add(scanTable1);

    ScanTable scanTable2 = TestTableScanManager.getScanTable();
    scanTable2.setTableName(TestHelper.TEST_TABLE_ROLE2);
    scanTable1.setScanInterval(-1);
    tableScanConfig.add(scanTable2);

    manager.init(tableScanConfig);
    manager.clean();
    manager.start();

  }
}
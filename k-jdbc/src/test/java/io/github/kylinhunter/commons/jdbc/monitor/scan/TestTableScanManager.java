package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.scan.ScanTable.ScanTableBuilder;
import javax.sql.DataSource;

class TestTableScanManager {

  public static void main(String[] args) {

    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);

    TableScanManager manager = new TableScanManager(dataSource);

    TableScanConfig tableScanConfig = new TableScanConfig();
    tableScanConfig.setServerId("1");
    tableScanConfig.setScheduleCorePoolSize(1);

    ScanTable scanTable1 = getScanTable();
    scanTable1.setTableName(TestHelper.TEST_TABLE_ROLE1);
    tableScanConfig.add(scanTable1);

    ScanTable scanTable2 = getScanTable();

    scanTable2.setTableName(TestHelper.TEST_TABLE_ROLE2);
    tableScanConfig.add(scanTable2);
    manager.init(tableScanConfig);
    manager.clean();
    manager.start();

  }


  public static ScanTable getScanTable() {
    ScanTableBuilder builder = ScanTable.builder();
    builder.tableName(TestHelper.TEST_TABLE_ROLE2)
        .tablePkName("id")
        .tableTimeName("sys_auto_updated")
        .destination(TestHelper.MONITOR_SCAN_TASK)
        .scanLimit(1).scanInterval(100);
    return builder.build();
  }
}
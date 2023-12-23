package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable.ScanTableBuilder;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.TableScanConfig;
import javax.sql.DataSource;

class TestScanTableMonitor {

  public static void main(String[] args) {

    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);

    TableScanConfig tableScanConfig = new TableScanConfig();
    tableScanConfig.setServerId("1");
    tableScanConfig.setScheduleCorePoolSize(1);

    ScanTable scanTable1 = getScanTable();
    scanTable1.setTableName(TestHelper.TEST_TABLE_ROLE1);
    tableScanConfig.add(scanTable1);

    ScanTable scanTable2 = getScanTable();

    scanTable2.setTableName(TestHelper.TEST_TABLE_ROLE2);
    tableScanConfig.add(scanTable2);
    TableMonitor tableMonitor = new ScanTableMonitor(dataSource, tableScanConfig);
    tableMonitor.init();
    tableMonitor.reset();
    tableMonitor.start();

  }


  public static ScanTable getScanTable() {
    ScanTableBuilder builder = ScanTable.builder();
    builder.tableName(TestHelper.TEST_TABLE_ROLE2)
        .database(TestHelper.DATABASE)
        .destination(TestHelper.MONITOR_SCAN_TASK)
        .tablePkName("id")
        .tableTimeName("sys_auto_updated")
        .scanLimit(1).scanInterval(100);
    return builder.build();
  }
}
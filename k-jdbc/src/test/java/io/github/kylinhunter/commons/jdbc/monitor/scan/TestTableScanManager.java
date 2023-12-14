package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import javax.sql.DataSource;

class TestTableScanManager {

  public static void main(String[] args) {

    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);

    TableScanManager manager = new TableScanManager(dataSource);
    TableScanConfig tableScanConfig = new TableScanConfig();
    tableScanConfig.setTableName(TestHelper.TEST_TABLE_ROLE);
    tableScanConfig.setTablePkName("id");
    tableScanConfig.setTableTimeName("sys_auto_updated");
    tableScanConfig.setScanLimit(1);
    tableScanConfig.setScanInterval(1000);

    manager.init(tableScanConfig);
    manager.scan(tableScanConfig);

  }
}
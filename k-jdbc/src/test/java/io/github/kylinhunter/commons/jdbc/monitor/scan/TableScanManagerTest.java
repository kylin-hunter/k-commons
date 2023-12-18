package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.scan.ScanTable.ScanTableBuilder;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

class TableScanManagerTest {

  @Test
  void init() {
    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);
    TableScanManager manager = new TableScanManager(dataSource);

    ScanTableBuilder builder = ScanTable.builder();
    builder.tableName(TestHelper.TEST_TABLE_ROLE1)
        .tablePkName("id")
        .tableTimeName("sys_auto_updated")
        .destination(TestHelper.MONITOR_SCAN_TASK)
        .scanLimit(100)
        .scanInterval(-1);
//
//    ScanTable scanTable = builder.build();
//    manager.init(scanTable);
//    manager.clean(scanTable);
//    manager.start();

  }
}
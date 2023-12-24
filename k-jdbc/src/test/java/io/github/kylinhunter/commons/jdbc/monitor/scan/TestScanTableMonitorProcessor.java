package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.TableScanConfig;
import io.github.kylinhunter.commons.jdbc.monitor.task.AbstractTaskProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.task.TestCallback;
import javax.sql.DataSource;

public class TestScanTableMonitorProcessor {

  public static void main(String[] args) {
    DataSource dataSource = new DataSourceManager(true).get();
    TestHelper.initTestSQl(dataSource);
    TableScanConfig tablescanConfig = TestScanTableMonitor.getTablescanConfig();

    AbstractTaskProcessor processor = new ScanTaskProcessor(dataSource, tablescanConfig);
    TestCallback testCallback = new TestCallback();
    processor.setExecCallback(testCallback);
    processor.reset();
    processor.start();
  }
}
package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.jdbc.TestDataSourceHelper;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.manager.ScanProgressManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableMonitorTaskManager;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ScanTableMonitorTest {

  @Test
  void init() throws SQLException {
    DataSource dataSource = TestDataSourceHelper.mockDataSource();
    ScanProgressManager scanProgressManager = Mockito.mock(ScanProgressManager.class);
    TableMonitorTaskManager tableMonitorTaskManager = Mockito.mock(TableMonitorTaskManager.class);

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
    ReflectUtils.setField(manager, "scanProgressManager", scanProgressManager);
    ReflectUtils.setField(manager, "tableMonitorTaskManager", tableMonitorTaskManager);

    manager.reset();
    manager.start();

  }
}
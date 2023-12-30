package io.github.kylinhunter.commons.jdbc.monitor.scan;

import static org.mockito.ArgumentMatchers.any;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.manager.ScanProgressManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.ScanRecordManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.TableScanConfig;
import io.github.kylinhunter.commons.jdbc.monitor.task.TaskProcessor;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ScanTableMonitorTest {

  @Test
  void init() throws SQLException {
    DataSource dataSource = TestHelper.mockDataSource();
    ScanProgressManager scanProgressManager = Mockito.mock(ScanProgressManager.class);

    ScanProgress scanProgress = new ScanProgress();

    Mockito.when(scanProgressManager.getLatestScanProgress(any())).thenReturn(scanProgress);

    ScanRecordManager scanRecordManager = Mockito.mock(ScanRecordManager.class);
    ScanRecord scanRecord = new ScanRecord();
    scanRecord.setId("11");
    scanRecord.setTime(LocalDateTime.now());
    Mockito.when(scanRecordManager.scanSameTime(any(), any())).thenReturn(
        ListUtils.newArrayList(scanRecord)).thenReturn(ListUtils.newArrayList());

    TableTaskManager tableTaskManager = Mockito.mock(TableTaskManager.class);

    TableScanConfig tableScanConfig = new TableScanConfig();
    tableScanConfig.setThreadPoolSize(1);

    ScanTable scanTable1 = TestScanTableMonitor.getScanTable();
    scanTable1.setTableName(TestHelper.TEST_TABLE_ROLE1);
    scanTable1.setDestination(TestHelper.TEST_SCAN_TASK);
    scanTable1.setScanInterval(-1);
    tableScanConfig.add(scanTable1);

    ScanTable scanTable2 = TestScanTableMonitor.getScanTable();
    scanTable2.setTableName(TestHelper.TEST_TABLE_ROLE2);
    scanTable1.setDestination(TestHelper.TEST_SCAN_TASK);
    scanTable1.setScanInterval(-1);
    tableScanConfig.add(scanTable2);
    TableMonitor manager = new ScanTableMonitor(dataSource, scanProgressManager, tableTaskManager,
        scanRecordManager,
        tableScanConfig);

    TaskProcessor taskProcessor = Mockito.mock(TaskProcessor.class);
    ReflectUtils.setField(manager, "taskProcessor", taskProcessor);
    manager.reset();
    manager.start();

  }
}
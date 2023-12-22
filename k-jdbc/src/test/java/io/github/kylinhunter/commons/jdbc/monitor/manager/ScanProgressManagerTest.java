package io.github.kylinhunter.commons.jdbc.monitor.manager;

import static org.mockito.ArgumentMatchers.any;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.dao.ScanProgressDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.TableScanConfig;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ScanProgressManagerTest {

  @Test
  void test() throws SQLException {
    ScanProgressManager manager = new ScanProgressManager(TestHelper.mockDataSource());

    ScanProgressDAO scanProgressDAO = Mockito.mock(ScanProgressDAO.class);
    ReflectUtils.setField(manager, "scanProgressDAO", scanProgressDAO);

    manager.ensureTableExists();

    manager.update("serverId", "", new ScanRecord());

    ScanProgress scanProgress = new ScanProgress("serverId", "", null, "99");
    Mockito.when(scanProgressDAO.findById(any(), any())).thenReturn(scanProgress);
    ScanTable scanTable = ScanTable.builder().build();
    scanTable.setConfig(new TableScanConfig());
    ScanProgress latestScanProgress = manager.getLatestScanProgress(scanTable);
    Assertions.assertEquals("99", latestScanProgress.getLastScanId());
    Assertions.assertEquals("serverId", latestScanProgress.getServerId());


  }
}
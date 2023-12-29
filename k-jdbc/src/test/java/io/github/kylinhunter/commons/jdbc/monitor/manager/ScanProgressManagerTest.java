package io.github.kylinhunter.commons.jdbc.monitor.manager;

import static org.mockito.ArgumentMatchers.any;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.ScanProgressDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
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

    manager.update("db", "", new ScanRecord());

    ScanProgress scanProgress = new ScanProgress("db", "", null, "99");
    Mockito.when(scanProgressDAO.findById(any(), any())).thenReturn(scanProgress);
    ScanTable scanTable = new ScanTable();
    ScanProgress latestScanProgress = manager.getLatestScanProgress(scanTable);
    Assertions.assertEquals("99", latestScanProgress.getLastScanId());
    Assertions.assertEquals("db", latestScanProgress.getDb());


  }
}
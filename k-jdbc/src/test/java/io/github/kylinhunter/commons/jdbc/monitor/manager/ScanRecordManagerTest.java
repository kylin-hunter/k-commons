package io.github.kylinhunter.commons.jdbc.monitor.manager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.dao.ScanRecordDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ScanRecordManagerTest {

  @Test
  void test() throws SQLException {

    DataSource dataSource = TestHelper.mockDataSource();
    ScanRecordManager scanRecordManager = new ScanRecordManager(dataSource);
    ScanRecordDAO scanRecordDAO = Mockito.mock(ScanRecordDAO.class);
    ReflectUtils.setField(scanRecordManager, "scanRecordDAO", scanRecordDAO);

    ScanRecord scanRecord = new ScanRecord();
    scanRecord.setId("1");
    when(scanRecordDAO.scanSameTime(any(), any(), any())).thenReturn(
        ListUtils.newArrayList(scanRecord));

    List<ScanRecord> scanRecords = scanRecordManager.scanSameTime(null, new ScanProgress());
    Assertions.assertEquals("1", scanRecords.get(0).getId());

    ScanRecord scanRecord2 = new ScanRecord();
    scanRecord2.setId("2");
    when(scanRecordDAO.scanNextTime(any(), any())).thenReturn(ListUtils.newArrayList(scanRecord2));
    List<ScanRecord> scanRecords2 = scanRecordManager.scanNextTime(null, new ScanProgress());
    Assertions.assertEquals("2", scanRecords2.get(0).getId());

  }

}
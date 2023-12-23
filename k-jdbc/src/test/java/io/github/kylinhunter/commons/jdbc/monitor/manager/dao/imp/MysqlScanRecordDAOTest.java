package io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MysqlScanRecordDAOTest {

  @SuppressWarnings("unchecked")
  @Test
  void test() throws SQLException {
    DataSource dataSource = TestHelper.mockDataSource();
    SqlExecutor sqlExecutor = Mockito.mock(SqlExecutor.class);
    MysqlScanRecordDAO dao = new MysqlScanRecordDAO(dataSource);
    ReflectUtils.setField(dao, "sqlExecutor", sqlExecutor);

    ScanTable scanTable = ScanTable.builder().build();
    ScanProgress scanProgress = new ScanProgress();
    ScanRecord scanRecord1 = new ScanRecord();
    scanRecord1.setId("1");
    when(sqlExecutor.query(anyString(), any(ResultSetHandler.class), any(), any(), any(),
        any())).thenReturn(ListUtils.newArrayList(scanRecord1));
    List<ScanRecord> scanRecords1 = dao.scanSameTime(scanTable, scanProgress.getNextScanTime(),
        scanProgress.getLastScanId());
    Assertions.assertEquals("1", scanRecords1.get(0).getId());

    ScanRecord scanRecord2 = new ScanRecord();
    scanRecord2.setId("2");
    when(sqlExecutor.query(anyString(), any(ResultSetHandler.class), any(), any(),
        any())).thenReturn(ListUtils.newArrayList(scanRecord2));
    List<ScanRecord> scanRecords2 = dao.scanNextTime(scanTable, null);
    Assertions.assertEquals("2", scanRecords2.get(0).getId());

  }
}
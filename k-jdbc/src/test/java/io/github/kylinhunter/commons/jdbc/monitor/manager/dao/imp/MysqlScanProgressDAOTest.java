package io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanProgress;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MysqlScanProgressDAOTest {

  @SuppressWarnings("unchecked")
  @Test
  void test() throws SQLException {

    DataSource dataSource = TestHelper.mockDataSource();
    SqlExecutor sqlExecutor = Mockito.mock(SqlExecutor.class);
    MysqlScanProgressDAO dao = new MysqlScanProgressDAO(dataSource);
    ReflectUtils.setField(dao, "sqlExecutor", sqlExecutor);

    dao.save(new ScanProgress());

    dao.delete("serverId", "tableName");

    dao.update(new ScanProgress());
    ScanProgress scanProgress = new ScanProgress();
    scanProgress.setLastScanId("999");
    when(sqlExecutor.query(anyString(), any(ResultSetHandler.class), any(), any())).thenReturn(
        scanProgress);

    ScanProgress findResult = dao.findById("serverId", "tableName");
    Assertions.assertEquals("999", findResult.getLastScanId());
  }
}
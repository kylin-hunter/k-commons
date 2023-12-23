package io.github.kylinhunter.commons.jdbc.monitor.manager;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.dao.TableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TableMonitorTaskManagerTest {

  @Test
  void test() throws SQLException {

    DataSource dataSource = TestHelper.mockDataSource();
    TableMonitorTaskDAO tableMonitorTaskDAO = Mockito.mock(TableMonitorTaskDAO.class);
    TableMonitorTaskManager manager = new TableMonitorTaskManager(dataSource);
    ReflectUtils.setField(manager, "tableMonitorTaskDAO", tableMonitorTaskDAO);

    ScanRecord scanRecord = new ScanRecord();
    ScanTable scanTable = ScanTable.builder().build();

    manager.saveOrUpdate(scanTable, scanRecord);
    manager.clean("destination", "databae", "testTable");
    manager.ensureDestinationExists("destination");


  }
}
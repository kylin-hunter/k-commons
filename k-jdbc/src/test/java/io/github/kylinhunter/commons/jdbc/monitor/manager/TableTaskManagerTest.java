package io.github.kylinhunter.commons.jdbc.monitor.manager;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.TableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TableTaskManagerTest {

  @Test
  void test() throws SQLException {

    DataSource dataSource = TestHelper.mockDataSource();
    TableMonitorTaskDAO tableMonitorTaskDAO = Mockito.mock(TableMonitorTaskDAO.class);
    TableTaskManager manager = new TableTaskManager(dataSource);
    ReflectUtils.setField(manager, "dao", tableMonitorTaskDAO);

    ScanRecord scanRecord = new ScanRecord();
    ScanTable scanTable = new ScanTable();

    manager.save(scanTable, scanRecord.getId(), RowOP.UPDATE);
    manager.clean("destination", "databae", "testTable");
    manager.ensureDestinationExists("destination");
    manager.findWaitDatas(scanTable.getDestination(), LocalDateTime.now(), 10);


  }
}
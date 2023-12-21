package io.github.kylinhunter.commons.jdbc.monitor.manager;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

class TableMonitorTaskManagerTest {

  @Test
  void test() throws SQLException {

    DataSource dataSource = TestHelper.mockDataSource();

    TableMonitorTaskManager manager = new TableMonitorTaskManager(dataSource);


  }
}
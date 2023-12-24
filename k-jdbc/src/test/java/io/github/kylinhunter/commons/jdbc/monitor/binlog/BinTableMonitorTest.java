package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import io.github.kylinhunter.commons.jdbc.binlog.BinLogClient;
import io.github.kylinhunter.commons.jdbc.binlog.BinLogClientTest;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinConfig;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BinTableMonitorTest {

  @Test
  void start() throws SQLException {

    BinConfig binConfig = BinLogClientTest.mockBinLogconfig();

    BinTable binTable1 = TestBinLogTableMonitor.getMonitorTable1();
    BinTable binTable2 = TestBinLogTableMonitor.getMonitorTable2();
    BinMonitorConfig binlogConfig = new BinMonitorConfig();
    binlogConfig.add(binTable1);
    binlogConfig.add(binTable2);
    BinLogClient binLogClient = Mockito.mock(BinLogClient.class);

    TableMonitor tableMonitor = new BinTableMonitor(binConfig, binlogConfig);

    ReflectUtils.setField(tableMonitor, "binLogClient", binLogClient);

    tableMonitor.reset();
    tableMonitor.start();
  }
}
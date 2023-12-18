package io.github.kylinhunter.commons.jdbc.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.MonitorTable;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.TableMonitorListener;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BinLogClientTest {

  @Test
  void init() {

    BinLogConfig binLogConfig = TestBinLogClient.getBinLogConfig();
    binLogConfig.setServerId(2);
    BinLogClient binLogClient = new BinLogClient(binLogConfig);

    BinaryLogClient binaryLogClient = Mockito.mock(BinaryLogClient.class);
    MonitorTable monitorTable = TestBinLogClient.getTableMonitorConfig();
    monitorTable.setDestination(TestHelper.MONITOR_BINLOG_TASK);
    TableMonitorListener tableMonitorListener = new TableMonitorListener();
    tableMonitorListener.setMonitorTable(monitorTable);
    binLogClient.addBinLogEventListener(tableMonitorListener);
    ReflectUtils.setField(binLogClient, "binaryLogClient", binaryLogClient);
    binLogClient.start();


  }
}
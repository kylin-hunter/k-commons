package io.github.kylinhunter.commons.jdbc.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TestBinLogEventListener;
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
    binLogClient.addBinLogEventListener(new TestBinLogEventListener());
    ReflectUtils.setField(binLogClient, "binaryLogClient", binaryLogClient);
    binLogClient.start();


  }
}
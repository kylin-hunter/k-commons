package io.github.kylinhunter.commons.jdbc.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinLogConfig;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TestBinLogEventListener;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.RedisSavePointManager;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BinLogClientTest {


  @Test
  void init() throws SQLException {

    BinLogConfig binLogConfig = mockBinLogconfig();
    RedisSavePointManager redisSavePointManager = Mockito.mock(RedisSavePointManager.class);
    binLogConfig.setSavePointManager(redisSavePointManager);
    BinLogClient binLogClient = new BinLogClient(binLogConfig);
    BinaryLogClient binaryLogClient = Mockito.mock(BinaryLogClient.class);
    binLogClient.addBinLogEventListener(new TestBinLogEventListener());
    ReflectUtils.setField(binLogClient, "binaryLogClient", binaryLogClient);
    binLogClient.start();


  }

  public static BinLogConfig mockBinLogconfig() throws SQLException {
    DataSource dataSource = TestHelper.mockDataSource();

    SavePointManager savePointManager = Mockito.mock(SavePointManager.class);
    BinLogConfig binLogConfig = Mockito.mock(BinLogConfig.class);
    Mockito.when(binLogConfig.toDataSource()).thenReturn(dataSource);
    Mockito.when(binLogConfig.getUrl()).thenReturn(TestHelper.MYSQL_JDBC_URL);
    Mockito.when(binLogConfig.getUsername()).thenReturn(TestHelper.MYSQL_USERNAME);
    Mockito.when(binLogConfig.getPassword()).thenReturn(TestHelper.MYSQL_PASSWORD);
    Mockito.when(binLogConfig.getJdbcUrl()).thenReturn(JdbcUtils.parse(TestHelper.MYSQL_JDBC_URL));
    Mockito.when(binLogConfig.getSavePointManager())
        .thenReturn(savePointManager);
    return binLogConfig;
  }
}
package io.github.kylinhunter.commons.jdbc.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinConfig;
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

    BinConfig binConfig = mockBinLogconfig();
    RedisSavePointManager redisSavePointManager = Mockito.mock(RedisSavePointManager.class);
    binConfig.setSavePointManager(redisSavePointManager);
    BinLogClient binLogClient = new BinLogClient(binConfig);
    BinaryLogClient binaryLogClient = Mockito.mock(BinaryLogClient.class);
    binLogClient.addBinLogEventListener(new TestBinLogEventListener());
    ReflectUtils.setField(binLogClient, "binaryLogClient", binaryLogClient);
    binLogClient.start();


  }

  public static BinConfig mockBinLogconfig() throws SQLException {
    DataSource dataSource = TestHelper.mockDataSource();

    SavePointManager savePointManager = Mockito.mock(SavePointManager.class);
    BinConfig binConfig = Mockito.mock(BinConfig.class);
    Mockito.when(binConfig.toDataSource()).thenReturn(dataSource);
    Mockito.when(binConfig.getUrl()).thenReturn(TestHelper.MYSQL_JDBC_URL);
    Mockito.when(binConfig.getUsername()).thenReturn(TestHelper.MYSQL_USERNAME);
    Mockito.when(binConfig.getPassword()).thenReturn(TestHelper.MYSQL_PASSWORD);
    Mockito.when(binConfig.getJdbcUrl()).thenReturn(JdbcUtils.parse(TestHelper.MYSQL_JDBC_URL));
    Mockito.when(binConfig.getSavePointManager())
        .thenReturn(savePointManager);
    return binConfig;
  }
}
package io.github.kylinhunter.commons.jdbc.binlog;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinConfig;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TestBinLogEventListener;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.MysqlSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.RedisSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;

public class TestBinLogClient {

  public static void main(String[] args) {
    BinConfig binConfig = getBinLogConfig();
    binConfig.setSavePointManager(getRedisSavePointManager1());
    BinLogClient binLogClient = new BinLogClient(binConfig);
    binLogClient.addBinLogEventListener(new TestBinLogEventListener());

    binLogClient.reset();
    binLogClient.start();
//    binLogClient.disconnect();
  }


  public static BinConfig getBinLogConfig() {
    BinConfig binConfig = new BinConfig();
    binConfig.setBinlogFilename(TestHelper.BINLOG_FILENAME);
    binConfig.setBinlogPosition(TestHelper.BINLOG_POS);
    binConfig.setUrl(TestHelper.MYSQL_JDBC_URL);
    binConfig.setUsername(TestHelper.MYSQL_USERNAME);
    binConfig.setPassword(TestHelper.MYSQL_PASSWORD);
    binConfig.setServerId(1);
    return binConfig;
  }


  public static RedisSavePointManager getRedisSavePointManager1() {
    RedisExecutor redisExecutor = TestHelper.getRedisExecutor();
    return new RedisSavePointManager(redisExecutor);
  }


  public static SavePointManager getRedisSavePointManager2() {
    RedisExecutor redisExecutor = TestHelper.getJDKRedisExecutor();
    return new RedisSavePointManager(redisExecutor);

  }


  public static SavePointManager getMysqlSavePointManager() {

    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(TestHelper.MYSQL_JDBC_URL2);
    hikariConfig.setUsername("root");
    hikariConfig.setPassword("root");
    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
    return new MysqlSavePointManager(hikariDataSource);

  }
}
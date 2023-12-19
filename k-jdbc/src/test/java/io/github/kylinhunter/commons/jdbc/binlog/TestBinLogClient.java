package io.github.kylinhunter.commons.jdbc.binlog;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TestBinLogEventListener;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.MysqlSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.RedisSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.JdkRedisCodec;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisConfig;

public class TestBinLogClient {

  public static void main(String[] args) {

    BinLogClient binLogClient = new BinLogClient(getBinLogConfig());
    binLogClient.addBinLogEventListener(new TestBinLogEventListener());

    binLogClient.reset();
    binLogClient.start();
//    binLogClient.disconnect();
  }


  public static BinLogConfig getBinLogConfig() {
    BinLogConfig binLogConfig = new BinLogConfig();
    binLogConfig.setBinlogFilename(TestHelper.BINLOG_FILENAME);
    binLogConfig.setBinlogPosition(TestHelper.BINLOG_POS);
    binLogConfig.setUrl(TestHelper.MYSQL_JDBC_URL);
    binLogConfig.setUsername(TestHelper.MYSQL_USERNAME);
    binLogConfig.setPassword(TestHelper.MYSQL_PASSWORD);
    binLogConfig.setSavePointManager(getRedisSavePointManager());
    binLogConfig.setServerId(1);
    return binLogConfig;
  }


  public static RedisSavePointManager getRedisSavePointManager() {
    RedisConfig redisConfig = TestHelper.getRedisConfig();
    return new RedisSavePointManager(redisConfig);
  }


  public static SavePointManager getRedisSavePointManager2() {
    RedisConfig redisConfig = TestHelper.getRedisConfig();
    redisConfig.setRedisCodec(new JdkRedisCodec());
    return new RedisSavePointManager(redisConfig);

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
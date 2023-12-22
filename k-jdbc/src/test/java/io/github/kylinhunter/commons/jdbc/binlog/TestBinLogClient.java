package io.github.kylinhunter.commons.jdbc.binlog;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.bean.BinLogConfig;
import io.github.kylinhunter.commons.jdbc.binlog.listener.TestBinLogEventListener;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.MysqlSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.RedisSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.JdkRedisCodec;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;

public class TestBinLogClient {

  public static void main(String[] args) {
    BinLogConfig binLogConfig = getBinLogConfig();
    binLogConfig.setSavePointManager(getRedisSavePointManager1());
    BinLogClient binLogClient = new BinLogClient(binLogConfig);
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
    binLogConfig.setServerId(1);
    return binLogConfig;
  }


  public static RedisSavePointManager getRedisSavePointManager1() {
    RedisConfig redisConfig = TestHelper.getRedisConfig();
    RedisExecutor redisExecutor = new RedisExecutor(redisConfig);
    return new RedisSavePointManager(redisExecutor);
  }


  public static SavePointManager getRedisSavePointManager2() {
    RedisConfig redisConfig = TestHelper.getRedisConfig();
    redisConfig.setRedisCodec(new JdkRedisCodec());
    RedisExecutor redisExecutor = new RedisExecutor(redisConfig);
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
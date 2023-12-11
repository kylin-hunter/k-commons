package io.github.kylinhunter.commons.jdbc.monitor.binlog;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.TableMonitorListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.redis.JdkRedisCodec;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.imp.MysqlSavePointManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.imp.RedisSavePointManager;

class TestBinLogClient {

  public static void main(String[] args) {

    String jdbcUrl = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    BinLogClient binLogClient = new BinLogClient(jdbcUrl, "root", "root");
    RedisSavePointManager redisSavePointManager = getRedisSavePointManager1();
//    redisSavePointManager.reset();
    binLogClient.setSavePointManager(redisSavePointManager);
    binLogClient.setBinlogFilename("binlog.000029");
    binLogClient.setBinlogPosition(1776364);
    binLogClient.setServerId(2);
    TableMonitorListener tableMonitorListener = new TableMonitorListener();
    tableMonitorListener.setTargetTableId(new TableId(TestHelper.DATABASE, TestHelper.TEST_TABLE));
    tableMonitorListener.setDestination(TestHelper.MONITOR_BINLOG_TASK);
    tableMonitorListener.setTargetTablePK("id");
    binLogClient.addBinLogEventListener(tableMonitorListener);

    binLogClient.start();
//    binLogClient.disconnect();
  }


  public static RedisSavePointManager getRedisSavePointManager1() {
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
    hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3307/kp?"
        + "useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
    hikariConfig.setUsername("root");
    hikariConfig.setPassword("root");
    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
    return new MysqlSavePointManager(hikariDataSource);

  }
}
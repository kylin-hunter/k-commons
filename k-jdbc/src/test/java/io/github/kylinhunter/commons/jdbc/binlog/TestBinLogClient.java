package io.github.kylinhunter.commons.jdbc.binlog;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.jdbc.binlog.redis.JdkRedisCodec;
import io.github.kylinhunter.commons.jdbc.binlog.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.MysqlSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.RedisSavePointManager;

class TestBinLogClient {

  public static void main(String[] args) {

    String jdbcUrl = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
    BinLogClient binLogClient = new BinLogClient(jdbcUrl, "root", "root");
    binLogClient.setSavePointManager(getRedisSavePointManager2());
    binLogClient.setBinlogFilename("binlog.000017");
    binLogClient.setBinlogPosition(19166811);
    binLogClient.setServerId(2);
    binLogClient.start();
//    binLogClient.disconnect();
  }


  public static RedisSavePointManager getRedisSavePointManager1() {
    RedisConfig redisConfig = new RedisConfig();
    redisConfig.setHost("127.0.0.1");
    redisConfig.setPort(6379);
    redisConfig.setPassword("123456");

    RedisSavePointManager redisSavePointManager = new RedisSavePointManager(redisConfig);
    redisSavePointManager.setRedisCodec(new JdkRedisCodec());
    return redisSavePointManager;
  }


  public static SavePointManager getRedisSavePointManager2() {
    RedisSavePointManager redisSavePointManager = getRedisSavePointManager1();
    redisSavePointManager.setRedisCodec(new JdkRedisCodec());
    return redisSavePointManager;
  }


  public static SavePointManager getMysqlSavePointManager() {

    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(
        "jdbc:mysql://localhost:3307/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
    hikariConfig.setUsername("root");
    hikariConfig.setPassword("root");
    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
    return new MysqlSavePointManager(hikariDataSource);

  }
}
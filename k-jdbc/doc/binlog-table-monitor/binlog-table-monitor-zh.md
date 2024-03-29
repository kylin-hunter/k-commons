# binlog-table-monitor

### 介绍

> 一个基于binlog-connector-java和redis的表监控工具。

实现的功能包括:
1. 监控表的变化记录到特定记录表中
2. 根据表的变化，提供回调函数

### 设计说明

![binlog-table-monitor](./binlog-table-monitor.png)

1. 利用 mysql-binlog-connector-java工具包，读取mysql的binlog
2. 读取过的位置记录在redis中，下次启动时，从redis中读取上次的位置
3. 被监控的表的变化，会被记录存储在一个或者几个固定的记录表中，表结构如下：
```
   CREATE TABLE IF NOT EXISTS `k_junit_table_monitor_binlog`
   (
   `db`               varchar(64) NOT NULL COMMENT 'database',
   `table_name`       varchar(64) NOT NULL COMMENT 'table-name',
   `sys_auto_updated` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'sys update time',
   `data_id`          varchar(64) NOT NULL COMMENT 'data_id',
   `retry_times`      tinyint     NOT NULL DEFAULT 0 COMMENT 'retry times',
   `op`               tinyint     NOT NULL DEFAULT 0 COMMENT '1、insert 2、update 3、delete',
   `status`           tinyint(4)  NOT NULL DEFAULT 0 COMMENT '0 wait，1 processing，2 success，3、retrying，4、error',
   PRIMARY KEY (`db`, `table_name`, `data_id`)
   );
```

4. 监控表的变化，会触发回调函数，需要用户自己实现 ExecCallback 接口

### 安装教程

#### 依赖说明（gradle.org）

```
    // mysql 驱动
    implementation("mysql:mysql-connector-java")
    // 读取binlog的工具包
    implementation 'com.zendesk:mysql-binlog-connector-java'
    // redis 驱动 用以存储binlog的记录的暂存点
    implementation 'io.lettuce:lettuce-core'
    // json 用以存储json在redis中的序列化
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

```


### 使用说明

#### 测试前准备监控的表

```

CREATE TABLE IF NOT EXISTS `k_junit_jdbc_role1`
(
    `id`                    varchar(64)  NOT NULL COMMENT '主键',
    `sys_tenant_id`         varchar(64)  NOT NULL DEFAULT '' COMMENT '租户ID',
    `sys_auto_updated`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '系统更新时间',
    `sys_created_user_id`   varchar(64)  NOT NULL default 0 COMMENT '创建人userid',
    `sys_created_user_name` varchar(64)  NOT NULL default '' COMMENT '创建人username',
    `sys_created_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_update_user_id`    varchar(64)  NOT NULL default 0 COMMENT '最后编辑人userid',
    `sys_update_user_name`  varchar(64)  NOT NULL default '' COMMENT '最后编辑人username',
    `sys_update_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后编辑时间',
    `sys_delete_flag`       tinyint      NOT NULL DEFAULT 0 COMMENT '0 未删除 1删除',
    `sys_op_lock`           int          NULL DEFAULT 0 COMMENT '乐观锁',
    `code`                  varchar(64)  NOT NULL DEFAULT '' COMMENT 'code',
    `name`                  varchar(64)  NOT NULL DEFAULT '' COMMENT 'name',
    `type`                  tinyint      NOT NULL DEFAULT 0 COMMENT '类型',
    `status`                tinyint      NOT NULL DEFAULT 0 COMMENT '角色状态，预留',
    `description`           varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_role_code` (`code`) COMMENT '唯一的code'
);

CREATE TABLE IF NOT EXISTS `k_junit_jdbc_role2`
(
    `id`                    varchar(64)  NOT NULL COMMENT '主键',
    `sys_tenant_id`         varchar(64)  NOT NULL DEFAULT '' COMMENT '租户ID',
    `sys_auto_updated`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '系统更新时间',
    `sys_created_user_id`   varchar(64)  NOT NULL default 0 COMMENT '创建人userid',
    `sys_created_user_name` varchar(64)  NOT NULL default '' COMMENT '创建人username',
    `sys_created_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_update_user_id`    varchar(64)  NOT NULL default 0 COMMENT '最后编辑人userid',
    `sys_update_user_name`  varchar(64)  NOT NULL default '' COMMENT '最后编辑人username',
    `sys_update_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后编辑时间',
    `sys_delete_flag`       tinyint      NOT NULL DEFAULT 0 COMMENT '0 未删除 1删除',
    `sys_op_lock`           int          NULL DEFAULT 0 COMMENT '乐观锁',
    `code`                  varchar(64)  NOT NULL DEFAULT '' COMMENT 'code',
    `name`                  varchar(64)  NOT NULL DEFAULT '' COMMENT 'name',
    `type`                  tinyint      NOT NULL DEFAULT 0 COMMENT '类型',
    `status`                tinyint      NOT NULL DEFAULT 0 COMMENT '角色状态，预留',
    `description`           varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_role_code` (`code`) COMMENT '唯一的code'
);


INSERT INTO k_junit_jdbc_role1 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('1', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:19:45', '0', '', '2023-12-09 15:19:45', 0, 0, '1', '1', 0, 0, '');
INSERT INTO k_junit_jdbc_role1 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('2', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '2', '2', 0, 0, '');
INSERT INTO k_junit_jdbc_role1 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('3', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '3', '3', 0, 0, '');
INSERT INTO k_junit_jdbc_role1 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('4', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '4', '4', 0, 0, '');
INSERT INTO k_junit_jdbc_role1 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('5', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '5', '5', 0, 0, '');
INSERT INTO k_junit_jdbc_role1 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('6', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '6', '6', 0, 0, '');
INSERT INTO k_junit_jdbc_role1 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,  sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('7', '', '2023-12-09 15:20:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '7', '7', 0, 0, '');


INSERT INTO k_junit_jdbc_role2 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('1', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:19:45', '0', '', '2023-12-09 15:19:45', 0, 0, '1', '1', 0, 0, '');
INSERT INTO k_junit_jdbc_role2 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('2', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '2', '2', 0, 0, '');
INSERT INTO k_junit_jdbc_role2 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('3', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '3', '3', 0, 0, '');
INSERT INTO k_junit_jdbc_role2 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('4', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '4', '4', 0, 0, '');
INSERT INTO k_junit_jdbc_role2 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('5', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '5', '5', 0, 0, '');
INSERT INTO k_junit_jdbc_role2 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('6', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '6', '6', 0, 0, '');
INSERT INTO k_junit_jdbc_role2 (id, sys_tenant_id, sys_auto_updated, sys_created_user_id,sys_created_user_name, sys_created_time, sys_update_user_id, sys_update_user_name, sys_update_time, sys_delete_flag, sys_op_lock, code, name, type, status, description) VALUES ('7', '', '2023-12-09 15:20:02', '0', '', '2023-12-09 15:21:02', '0', '', '2023-12-09 15:21:02', 0, 0, '7', '7', 0, 0, '');


```
#### 示例代码
```java
package io.github.kylinhunter.jdbc;

import io.github.kylinhunter.commons.jdbc.binlog.bean.BinConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.RedisSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.ClusterRedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.ClusterRedisExecutor;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.SingleRedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.SingleRedisExecutor;
import io.github.kylinhunter.commons.jdbc.exception.FastFailException;
import io.github.kylinhunter.commons.jdbc.monitor.TableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.BinTableMonitor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinMonitorConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.jdbc.monitor.task.AbstractRowListener;
import lombok.extern.slf4j.Slf4j;

class TestBinTableMonitor {

  /**
   * 获取RedisSavePointManager实例，用于保存binlog 读取 的 进度点
   *
   * @return RedisSavePointManager实例（单机版redis）
   */
  public static RedisSavePointManager getRedisSavePointManagerForSingle() {
    SingleRedisConfig redisConfig = new SingleRedisConfig();
    redisConfig.setHost("127.0.0.1");
    redisConfig.setPort(6379);
    redisConfig.setPassword("123456");
    RedisExecutor redisExecutor = new SingleRedisExecutor(redisConfig);
    return new RedisSavePointManager(redisExecutor);
  }

  /**
   * 获取RedisSavePointManager实例，用于保存binlog 读取 的 进度点
   *
   * @return RedisSavePointManager实例 (集群版redis）
   */

  public static RedisSavePointManager getRedisSavePointManagerForCluster() {
    ClusterRedisConfig redisConfig = new ClusterRedisConfig();
    redisConfig.addNode("127.0.0.1",7361);
    redisConfig.addNode("127.0.0.1",7362);
    redisConfig.addNode("127.0.0.1",7363);
    redisConfig.addNode("127.0.0.1",7364);
    redisConfig.addNode("127.0.0.1",7365);
    redisConfig.addNode("127.0.0.1",7366);

    redisConfig.setPassword("123456");
    RedisExecutor redisExecutor = new ClusterRedisExecutor(redisConfig);
    return new RedisSavePointManager(redisExecutor);
  }

  /**
   * 获取BinConfig对象，用于指定读取那个mysql的哪个binlog文件名
   *
   * @return BinConfig 返回一个BinConfig对象
   */
  public static BinConfig getBinLogConfig() {
    BinConfig binConfig = new BinConfig();
    binConfig.setBinlogFilename("binlog.000047"); // 指定binlog文件名
    binConfig.setBinlogPosition(0); // 指定binlog文件起始位置
    binConfig.setUrl("jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
    binConfig.setUsername("root");
    binConfig.setPassword("root");
    binConfig.setSavePointManager(getRedisSavePointManagerForCluster()); // 指定保存binlog读取进度点

    return binConfig;
  }


  /**
   * 获取BinMonitorConfig实例
   *
   * @return BinMonitorConfig实例
   */
  public static BinMonitorConfig getBinMonitorConfig() {
    BinMonitorConfig monitorConfig = new BinMonitorConfig();

    BinTable binTable = new BinTable(); // 指定监控的表
    binTable.setPkColName("id"); // 指定主键列名
    binTable.setDatabase("kp"); // 指定数据库名
    binTable.setTableName("k_junit_jdbc_role1"); // 指定表名
    binTable.setDestination("k_junit_table_monitor_binlog"); // 指定监控信息保存在哪里，表结构在上面定义的
    monitorConfig.add(binTable);
    monitorConfig.setMaxRetryTimes(1); // 指定失败重试次数
    // monitorConfig.add(binTable2); 支持多个表
    return monitorConfig;
  }



  /***
   * @title 测试行变化的回调函数
   */
  @Slf4j
  public static class TestRowListener extends AbstractRowListener {

    /**
     * @param database  database
     * @param tableName tableName
     * @param dataId    dataId
     * @title insert
     * @description insert
     * @author BiJi'an
     * @date 2023-12-28 17:08
     */
    @Override
    public void insert(String database, String tableName, String dataId) {
      log.info("inser to database:{},tableName:{},dataId:{}", database, tableName, dataId);
      // 模拟业务处理
      if (dataId.equals("1")) {
        log.info("模拟处理  数据1  insert 事件处理 成功 。。。。。。");
      } else if (dataId.equals("2")) {
        throw new FastFailException("模拟处理 数据2  的 insert 事件处理 快速失败 ，不再处理 。。。。。。");
      } else {
        throw new RuntimeException("模拟处理其他数据的 insert 事件，发生失败 ,默认会重试3次....");
      }
    }

    /**
     * @param database  database
     * @param tableName tableName
     * @param dataId    dataId
     * @title update
     * @description update
     * @author BiJi'an
     * @date 2023-12-28 17:08
     */
    @Override
    public void update(String database, String tableName, String dataId) {
      log.info("update to database:{},tableName:{},dataId:{}", database, tableName, dataId);
      // 模拟业务处理
      if (dataId.equals("1")) {
        log.info("模拟处理  数据1  insert 事件处理 成功 。。。。。。");
      } else if (dataId.equals("2")) {
        throw new FastFailException("模拟处理 数据2  的 update 事件处理 快速失败 ，不再处理 。。。。。。");
      } else {
        throw new RuntimeException("模拟处理其他数据的 update 事件，发生失败 ,默认会重试3次....");
      }
    }

    /**
     * @param database  database
     * @param tableName tableName
     * @param dataId    dataId
     * @title delete
     * @description delete
     * @author BiJi'an
     * @date 2023-12-28 17:08
     */
    @Override
    public void delete(String database, String tableName, String dataId) {
      log.info("delete to database:{},tableName:{},dataId:{}", database, tableName, dataId);
      // 模拟业务处理
      if (dataId.equals("1")) {
        log.info("模拟处理  数据1  insert 事件处理 成功 。。。。。。");
      } else if (dataId.equals("2")) {
        throw new FastFailException("模拟处理 数据2  的 update 事件处理 快速失败 ，不再处理 。。。。。。");
      } else {
        throw new RuntimeException("模拟处理其他数据的 delete 事件，发生失败 ,默认会重试3次....");
      }
    }
  }

  /**
   * 测试入口
   */

  public static void main(String[] args) {
    TableMonitor tableMonitor = new BinTableMonitor(getBinLogConfig(), getBinMonitorConfig());
    tableMonitor.reset(); // 重置暂存点，如果需要重新读取binlog，需要调用该方法
    tableMonitor.setRowListener(new TestRowListener()); // 设置回调函数
    tableMonitor.start(); // 启动监控
  }
}

```

#### 最终结果
```
 //查看监控记录表中最新状态
  SELECT t.* FROM kp.k_junit_table_monitor_binlog t 
```
![result](./result.png)

> 观察上图可以看到

1. 表j_junit_jdbc_role1的row （1）    用户拿到了相关事件，并且处理成功 status=2
2. 表j_junit_jdbc_role1的row （2）    用户拿到了相关事件，并且主动放弃处理,设置为失败  status=4
3. 表j_junit_jdbc_role1的row （3-7） 用户拿到了相关事件，并且一直处理不成功，重试四次后，最终失败status=4


### 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

package io.github.kylinhunter.commons.jdbc.binlog.redis;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.ClusterRedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.ClusterRedisExecutor;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class ClusterRedisExecutorTest {

  @Test
  void test() {
    ClusterRedisConfig clusterRedisConfig = TestHelper.getClusterRedisConfig();

    RedisClusterClient mockRedisClient = Mockito.mock(RedisClusterClient.class);

    StatefulRedisClusterConnection<String, Object> connection = Mockito.mock(
        StatefulRedisClusterConnection.class);

    RedisAdvancedClusterCommands<String, Object> redisCommands = Mockito.mock(
        RedisAdvancedClusterCommands.class);

    Mockito.when(mockRedisClient.connect(clusterRedisConfig.getRedisCodec()))
        .thenReturn(connection);

    Mockito.when(connection.sync()).thenReturn(redisCommands);

    try (MockedStatic<RedisClusterClient> mock = Mockito.mockStatic(RedisClusterClient.class)) {
      mock.when(() -> {
        RedisClusterClient.create(Mockito.any(Iterable.class));
      }).thenReturn(mockRedisClient);

      RedisExecutor executor = new ClusterRedisExecutor(clusterRedisConfig);
      executor.set("RedisExecutorTest", "1");

      String v = executor.get("RedisExecutorTest");
      System.out.println("value: " + v);

      executor.shutdown();
    }

  }
}
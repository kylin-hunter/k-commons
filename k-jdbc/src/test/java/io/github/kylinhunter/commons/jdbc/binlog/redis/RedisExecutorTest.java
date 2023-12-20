package io.github.kylinhunter.commons.jdbc.binlog.redis;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class RedisExecutorTest {

  @Test
  void test() {
    RedisConfig redisConfig = TestHelper.getRedisConfig();

    RedisClient mockRedisClient = Mockito.mock(RedisClient.class);

    StatefulRedisConnection<String, Object> connection = Mockito.mock(
        StatefulRedisConnection.class);

    RedisCommands<String, Object> redisCommands = Mockito.mock(RedisCommands.class);

    Mockito.when(mockRedisClient.connect(redisConfig.getRedisCodec())).thenReturn(connection);

    Mockito.when(connection.sync()).thenReturn(redisCommands);

    try (MockedStatic<RedisClient> mock = Mockito.mockStatic(RedisClient.class)) {
      mock.when(() -> {
        RedisClient.create(Mockito.any(RedisURI.class));
      }).thenReturn(mockRedisClient);

      RedisExecutor executor = new RedisExecutor(redisConfig);
      executor.set("RedisExecutorTest", "1");

      String v = executor.get("RedisExecutorTest");
      System.out.println("value: " + v);

      executor.shutdown();
    }

  }
}
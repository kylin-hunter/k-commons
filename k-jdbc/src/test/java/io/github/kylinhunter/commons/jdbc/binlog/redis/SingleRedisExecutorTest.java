package io.github.kylinhunter.commons.jdbc.binlog.redis;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.SingleRedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.SingleRedisExecutor;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class SingleRedisExecutorTest {

  @Test
  void test() {
    SingleRedisConfig singleRedisConfig = TestHelper.getSingleRedisConfig();

    RedisClient mockRedisClient = Mockito.mock(RedisClient.class);

    StatefulRedisConnection<String, Object> connection = Mockito.mock(
        StatefulRedisConnection.class);

    RedisCommands<String, Object> redisCommands = Mockito.mock(RedisCommands.class);

    Mockito.when(mockRedisClient.connect(singleRedisConfig.getRedisCodec())).thenReturn(connection);

    Mockito.when(connection.sync()).thenReturn(redisCommands);

    try (MockedStatic<RedisClient> mock = Mockito.mockStatic(RedisClient.class)) {
      mock.when(() -> RedisClient.create(Mockito.any(RedisURI.class))).thenReturn(mockRedisClient);

      RedisExecutor executor = new SingleRedisExecutor(singleRedisConfig);
      executor.set("RedisExecutorTest", "1");

      String v = executor.get("RedisExecutorTest");
      System.out.println("value: " + v);

      executor.shutdown();
    }

  }
}
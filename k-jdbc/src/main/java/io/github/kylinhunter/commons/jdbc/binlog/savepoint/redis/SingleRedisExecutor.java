/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis;

import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.RedisURI.Builder;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 20:20
 */
public class SingleRedisExecutor implements RedisExecutor {

  private final RedisCommands<String, Object> redisCommands;
  private final StatefulRedisConnection<String, Object> connection;

  public SingleRedisExecutor(SingleRedisConfig singleRedisConfig) {
    Builder builder =
        RedisURI.builder()
            .withHost(singleRedisConfig.getHost())
            .withPort(singleRedisConfig.getPort());
    if (!StringUtil.isEmpty(singleRedisConfig.getPassword())) {
      builder = builder.withPassword(singleRedisConfig.getPassword());
    }
    if (singleRedisConfig.getTimeout() > 0) {
      builder =
          builder.withTimeout(Duration.of(singleRedisConfig.getTimeout(), ChronoUnit.SECONDS));
    }

    RedisURI redisUri = builder.build();
    RedisClient redisClient = RedisClient.create(redisUri);
    this.connection = redisClient.connect(singleRedisConfig.getRedisCodec());
    this.redisCommands = connection.sync();
  }

  /**
   * @param key key
   * @param value value
   * @return java.lang.String
   * @title set
   * @description set
   * @author BiJi'an
   * @date 2023-12-10 20:26
   */
  public String set(String key, Object value) {
    return this.redisCommands.set(key, value);
  }

  /**
   * @param key key
   * @return V
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2023-12-10 20:31
   */
  @SuppressWarnings("unchecked")
  public <V> V get(String key) {
    return (V) this.redisCommands.get(key);
  }

  /**
   * @title shutdown
   * @description shutdown
   * @author BiJi'an
   * @date 2023-12-17 10:23
   */
  public void shutdown() {
    IOUtil.closeQuietly(this.connection);
  }
}

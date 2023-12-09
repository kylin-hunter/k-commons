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
package io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp;

import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.jdbc.binlog.redis.JsonRedisCodec;
import io.github.kylinhunter.commons.jdbc.binlog.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.RedisURI.Builder;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */
public class RedisSavePointManager implements SavePointManager {

  private RedisCommands<String, Object> redisCommands;

  private StatefulRedisConnection<String, Object> connection;
  @Setter private String recentBinLogKey = "binlog_process";

  @Setter private RedisCodec<String, Object> redisCodec = new JsonRedisCodec();

  private final RedisURI redisUri;

  public RedisSavePointManager(RedisConfig redisConfig) {
    Builder builder =
        RedisURI.builder().withHost(redisConfig.getHost()).withPort(redisConfig.getPort());
    if (!StringUtil.isEmpty(redisConfig.getPassword())) {
      builder = builder.withPassword(redisConfig.getPassword());
    }
    if (redisConfig.getTimeout() > 0) {
      builder = builder.withTimeout(Duration.of(redisConfig.getTimeout(), ChronoUnit.SECONDS));
    }

    this.redisUri = builder.build();
  }

  @Override
  public void reset() {
    this.redisCommands.set(recentBinLogKey, this.getDefaultSavePoint());
  }

  @Override
  public void save(SavePoint savePoint) {
    this.redisCommands.set(recentBinLogKey, savePoint);
  }

  @Override
  public SavePoint get() {
    return (SavePoint) this.redisCommands.get(recentBinLogKey);
  }

  @Override
  public void init(JdbcUrl jdbcUrl) {
    RedisClient redisClient = RedisClient.create(redisUri);
    this.connection = redisClient.connect(this.redisCodec);
    this.redisCommands = connection.sync();
    SavePoint savePoint = this.get();
    if (savePoint == null) {
      savePoint = this.getDefaultSavePoint();
      this.redisCommands.set(recentBinLogKey, savePoint);
    }
  }

  @Override
  public void shutdown() {
    IOUtil.closeQuietly(connection);
  }
}

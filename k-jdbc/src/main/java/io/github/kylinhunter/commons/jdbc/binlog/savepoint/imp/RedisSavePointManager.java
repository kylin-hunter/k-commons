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

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean.RedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean.SavePoint;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.RedisURI.Builder;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */
public class RedisSavePointManager implements SavePointManager {

  private final RedisCommands<String, String> redisCommands;

  @Setter private String recentBinLogKey = "binlog_process";

  public RedisSavePointManager(RedisConfig redisConfig) {
    Builder builder =
        RedisURI.builder().withHost(redisConfig.getHost()).withPort(redisConfig.getPort());
    if (!StringUtil.isEmpty(redisConfig.getPassword())) {
      builder = builder.withPassword(redisConfig.getPassword());
    }
    if (redisConfig.getTimeout() > 0) {
      builder = builder.withTimeout(Duration.of(redisConfig.getTimeout(), ChronoUnit.SECONDS));
    }
    RedisURI redisUri = builder.build();
    RedisClient redisClient = RedisClient.create(redisUri);
    StatefulRedisConnection<String, String> connection = redisClient.connect();
    this.redisCommands = connection.sync();
  }

  @Override
  public void reset() {
    this.redisCommands.set(
        recentBinLogKey, DEAFULT_SAVEPOINT.getName() + "#" + DEAFULT_SAVEPOINT.getPosition());
  }

  @Override
  public void save(SavePoint savePoint) {
    this.redisCommands.set(recentBinLogKey, savePoint.getName() + "#" + savePoint.getPosition());
  }

  @Override
  public SavePoint getLatest() {
    String sp = this.redisCommands.get(recentBinLogKey);
    if (!StringUtil.isEmpty(sp)) {
      String[] split = StringUtil.split(sp, '#');
      return new SavePoint(split[0], Long.parseLong(split[1]));
    }
    return null;
  }

  @Override
  public void init() {
    SavePoint savePoint = this.getLatest();
    if (savePoint == null) {
      this.redisCommands.set(
          recentBinLogKey, DEAFULT_SAVEPOINT.getName() + "#" + DEAFULT_SAVEPOINT.getPosition());
    }
  }
}

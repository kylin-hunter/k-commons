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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.imp;

import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.redis.RedisExecutor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import java.util.Objects;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */
@Slf4j
public class RedisSavePointManager implements SavePointManager {

  @Setter private String recentBinLogKey = "binlog_process";
  private final RedisExecutor redisExecutor;

  public RedisSavePointManager(RedisConfig redisConfig) {
    redisExecutor = new RedisExecutor(redisConfig);
  }

  @Override
  public void reset() {
    this.redisExecutor.set(recentBinLogKey, this.getDefaultSavePoint());
  }

  @Override
  public void save(SavePoint savePoint) {
    this.redisExecutor.set(recentBinLogKey, savePoint);
    log.info("save point : {}/{}", savePoint.getName(), savePoint.getPosition());
  }

  @Override
  public SavePoint get() {
    return this.redisExecutor.get(recentBinLogKey);
  }

  @Override
  public void init(JdbcUrl jdbcUrl) {
    Objects.requireNonNull(jdbcUrl);
    SavePoint savePoint = this.get();
    if (savePoint == null) {
      this.redisExecutor.set(recentBinLogKey, this.getDefaultSavePoint());
    }
  }
}

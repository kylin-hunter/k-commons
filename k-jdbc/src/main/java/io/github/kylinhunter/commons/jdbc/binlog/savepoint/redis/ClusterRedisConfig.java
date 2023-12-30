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

import io.github.kylinhunter.commons.collections.MapUtils;
import io.lettuce.core.codec.RedisCodec;
import java.util.Map;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-05 20:26
 */
@Data
public class ClusterRedisConfig {

  private Map<String, Integer> nodes = MapUtils.newHashMap();
  private CharSequence password;
  private long timeout;

  /**
   * @param host host
   * @param port port
   * @title add
   * @description add
   * @author BiJi'an
   * @date 2023-12-30 00:43
   */
  public void addNode(String host, int port) {
    this.nodes.put(host, port);
  }

  private RedisCodec<String, Object> redisCodec = new JsonRedisCodec();
}

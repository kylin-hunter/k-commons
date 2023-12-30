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

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.io.IOUtil;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import java.time.Duration;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 20:20
 */
public class ClusterRedisExecutor implements RedisExecutor {

  private final RedisAdvancedClusterCommands<String, Object> redisCommands;
  private final StatefulRedisClusterConnection<String, Object> connection;

  public ClusterRedisExecutor(ClusterRedisConfig clusterRedisConfig) {

    List<RedisURI> nodes = ListUtils.newArrayList();
    clusterRedisConfig
        .getNodes()
        .forEach(
            (host, port) -> {
              RedisURI node = RedisURI.create(host, port);
              node.setPassword(clusterRedisConfig.getPassword());
              nodes.add(node);
            });

    RedisClusterClient clusterClient = RedisClusterClient.create(nodes);

    ClusterTopologyRefreshOptions topologyRefreshOptions =
        ClusterTopologyRefreshOptions.builder()
            .enablePeriodicRefresh(Duration.ofMinutes(10))
            .enableAllAdaptiveRefreshTriggers()
            .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30))
            .build();

    clusterClient.setOptions(
        ClusterClientOptions.builder().topologyRefreshOptions(topologyRefreshOptions).build());

    if (clusterRedisConfig.getTimeout() > 0) {
      clusterClient.setDefaultTimeout(Duration.ofSeconds(clusterRedisConfig.getTimeout()));
    }

    this.connection = clusterClient.connect(clusterRedisConfig.getRedisCodec());
    this.redisCommands = this.connection.sync();
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

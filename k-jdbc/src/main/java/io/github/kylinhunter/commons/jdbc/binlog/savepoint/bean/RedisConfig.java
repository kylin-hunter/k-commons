package io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-05 20:26
 */
@Data
public class RedisConfig {

  private String host;
  private int port;
  private CharSequence password;
  private long timeout;

}
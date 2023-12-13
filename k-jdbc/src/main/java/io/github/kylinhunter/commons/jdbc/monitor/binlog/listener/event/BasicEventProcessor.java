package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 10:33
 */
public abstract class BasicEventProcessor implements EventProcessor {

  @Setter
  protected DatabaseMetaCache databaseMetaCache;


}
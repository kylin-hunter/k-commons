package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.BinLogEventListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:22
 */
public interface EventProcessor {

  void process(BinLogEventListener binLogEventListener, EventData data,
      Context context);


}
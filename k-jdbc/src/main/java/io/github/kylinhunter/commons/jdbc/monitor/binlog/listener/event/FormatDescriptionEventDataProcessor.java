package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.FormatDescriptionEventData;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.BinLogEventListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:23
 */
@Slf4j
public class FormatDescriptionEventDataProcessor extends BasicEventProcessor {

  /**
   * @param data data
   * @title processEventRotate
   * @description processEventRotate
   * @author BiJi'an
   * @date 2023-12-10 01:07
   */
  public void process(BinLogEventListener binLogEventListener, EventData data,
      Context context) {
    if (data instanceof FormatDescriptionEventData) {
      FormatDescriptionEventData eventData = (FormatDescriptionEventData) data;
      int binlogVersion = eventData.getBinlogVersion();
      String serverVersion = eventData.getServerVersion();
      log.info("binVersion={},serverVersion={}", binlogVersion, serverVersion);
    }
  }

}
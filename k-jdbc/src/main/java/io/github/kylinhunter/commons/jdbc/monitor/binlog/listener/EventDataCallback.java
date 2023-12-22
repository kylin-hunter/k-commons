package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.MonitorTable;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-23 02:03
 */

@FunctionalInterface
public interface EventDataCallback<T extends EventData> {

  void callback(MonitorTable monitorTable, T eventData, ColumnMeta pkColumnMeta);

}
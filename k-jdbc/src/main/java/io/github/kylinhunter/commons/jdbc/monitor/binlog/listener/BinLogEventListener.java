package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.SavePointManager;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 21:54
 */
public interface BinLogEventListener extends EventListener {

  void setSavePointManager(SavePointManager savePointManager);

  void init(DataSource dataSource);
}
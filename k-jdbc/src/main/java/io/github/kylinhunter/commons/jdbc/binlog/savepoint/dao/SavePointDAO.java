package io.github.kylinhunter.commons.jdbc.binlog.savepoint.dao;

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;

public interface SavePointDAO {

  void save(SavePoint savePoint);

  SavePoint get();

  void update(SavePoint savePoint);


  JdbcUrl getJdbcUrl();

  void ensureTableExists();


}

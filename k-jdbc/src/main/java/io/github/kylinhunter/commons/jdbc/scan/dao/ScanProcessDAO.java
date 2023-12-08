package io.github.kylinhunter.commons.jdbc.scan.dao;

import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.scan.dao.entity.ScanProgress;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:50
 */
public interface ScanProcessDAO {

  ScanProgress findById(String id);

  void save(ScanProgress scanProgress);

  void update(ScanProgress scanProgress);

  void init();

  SqlExecutor getSqlExecutor();
}
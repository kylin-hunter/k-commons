package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 00:44
 */
public interface DatabaseMetaCache {

  /**
   * @param no no
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-31 15:47
   */
  DatabaseMeta getByDsNo(int no);

  /**
   * @param name name
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getByDsName
   * @description
   * @author BiJi'an
   * @date 2023-05-31 15:49
   */
  DatabaseMeta getByDsName(String name);
}

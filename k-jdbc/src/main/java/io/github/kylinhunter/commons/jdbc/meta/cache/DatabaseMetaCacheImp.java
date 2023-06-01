package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.cache.guava.AbstractCache;
import io.github.kylinhunter.commons.cache.guava.CacheConfig;
import io.github.kylinhunter.commons.cache.guava.CacheKey;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 00:44
 */
@C
@RequiredArgsConstructor
public class DatabaseMetaCacheImp extends AbstractCache<Optional<DatabaseMeta>> implements
    DatabaseMetaCache {

  private final DatabaseMetaReader databaseMetaReader;

  @Override
  @Nonnull
  public Optional<DatabaseMeta> load(CacheKey cacheKey) {


    int datasourceNo = cacheKey.getInt(0);
    DataSourceEx dataSourceEx = DataSourceUtils.getByNo(datasourceNo);
    if (dataSourceEx != null) {
      return Optional.ofNullable(databaseMetaReader.getDatabaseMetaData(dataSourceEx));
    } else {
      return Optional.empty();
    }
  }

  @Override
  protected void custom(CacheConfig cacheConfig) {
  }

  /**
   * @param no no
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-31 15:47
   */
  public DatabaseMeta getByDsNo(int no) {
    Optional<DatabaseMeta> databaseMeta = super.get(no);
    if (databaseMeta.isPresent()) {
      return databaseMeta.get();
    }
    return null;
  }

  /**
   * @param name name
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getByDsName
   * @description
   * @author BiJi'an
   * @date 2023-05-31 15:49
   */
  public DatabaseMeta getByDsName(String name) {
    DataSourceEx dataSourceEx = DataSourceUtils.getByName(name);
    if (dataSourceEx != null) {
      Optional<DatabaseMeta> databaseMeta = super.get(dataSourceEx.getDsNo());
      if (databaseMeta.isPresent()) {
        return databaseMeta.get();
      }
      return null;

    } else {
      return null;
    }
  }
}

/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import io.github.kylinhunter.commons.utils.cache.guava.AbstractCache;
import io.github.kylinhunter.commons.utils.cache.guava.CacheConfig;
import io.github.kylinhunter.commons.utils.cache.guava.CacheKey;
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
public class DatabaseMetaCacheImp extends AbstractCache<Optional<DatabaseMeta>>
    implements DatabaseMetaCache {

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

package io.github.kylinhunter.commons.jdbc.meta.cache;

import io.github.kylinhunter.commons.cache.guava.AbstractCache;
import io.github.kylinhunter.commons.cache.guava.CacheConfig;
import io.github.kylinhunter.commons.cache.guava.CacheKey;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 00:44
 **/
@C
@RequiredArgsConstructor
public class DatabaseMetaCache extends AbstractCache<DatabaseMeta> {
    private final DatabaseMetaReader databaseMetaReader;

    @Override
    public DatabaseMeta load(CacheKey cacheKey) {
        int datasourceNo = cacheKey.getInt(0);
        DataSourceEx dataSourceEx = DataSourceUtils.getByNo(datasourceNo);
        return databaseMetaReader.getDatabaseMetaData(dataSourceEx);
    }

    @Override
    protected void custom(CacheConfig cacheConfig) {

    }
}

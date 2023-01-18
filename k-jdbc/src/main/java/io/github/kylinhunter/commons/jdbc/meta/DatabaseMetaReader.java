package io.github.kylinhunter.commons.jdbc.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 **/
@Slf4j
@C
public class DatabaseMetaReader {

    /**
     * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
     * @title getDatabaseMetaData
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:41
     */
    public DatabaseMeta getDatabaseMetaData() {
        return this.getDatabaseMetaData(DataSourceUtils.getDefaultDataSource());

    }

    /**
     * @param dataSource dataSource
     * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
     * @title getDatabaseMetaData
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:41
     */
    public DatabaseMeta getDatabaseMetaData(DataSourceEx dataSource) {

        try (Connection connection = dataSource.getConnection()) {
            return getDatabaseMetaData(connection);
        } catch (Exception e) {
            throw new JdbcException("getDatabaseMetaData error", e);
        }

    }

    /**
     * @param connection connection
     * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
     * @title getDatabaseMetaData
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:41
     */

    public DatabaseMeta getDatabaseMetaData(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            DatabaseMeta databaseMeta = new DatabaseMeta();
            databaseMeta.setUrl(metaData.getURL());
            databaseMeta.setProductName(metaData.getDatabaseProductName());
            databaseMeta.setVersion(metaData.getDatabaseProductVersion());
            databaseMeta.setDriverName(metaData.getDriverName());
            return databaseMeta;
        } catch (Exception e) {
            throw new JdbcException("getDatabaseMetaData error", e);
        }
    }

}





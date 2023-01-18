package io.github.kylinhunter.commons.jdbc.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.cache.guava.Cache;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.constant.ColumnParserType;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import io.github.kylinhunter.commons.jdbc.meta.parser.ColumnParser;
import io.github.kylinhunter.commons.util.ObjectValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 **/
@Slf4j
@C
@RequiredArgsConstructor
public class ColumnMetaReader {
    private final Cache <DatabaseMeta>databaseCache;
    private final DatabaseMetaReader databaseMetaReader;

    /**
     * @param schema    schema
     * @param tableName tableName
     * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
     * @title getColumnMetaData
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:42
     */
    public List<ColumnMeta> getColumnMetaData(String schema, String tableName) {
        return getColumnMetaData(DataSourceUtils.getDefaultDataSource(), schema, tableName);
    }

    /**
     * @param dataSource dataSource
     * @param schema     schema
     * @param tableName  tableName
     * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
     * @title getColumnMetaData
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:42
     */
    public List<ColumnMeta> getColumnMetaData(DataSourceEx dataSource, String schema, String tableName) {
        DatabaseMeta databaseMeta = databaseCache.get(dataSource.getNo());
        try (Connection connection = dataSource.getConnection()) {
            return getColumnMetaData(databaseMeta, connection, null, schema, tableName);

        } catch (JdbcException e) {
            throw e;
        } catch (Exception e) {
            throw new JdbcException("getColumnMetaData error", e);
        }

    }

    private List<ColumnMeta> getColumnMetaData(Connection connection, String catalog, String schema,
                                               String tableName) {
        return getColumnMetaData(null, connection, catalog, schema, tableName);

    }

    /**
     * @param connection connection
     * @param catalog    catalog
     * @param schema     schema
     * @param tableName  tableName
     * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
     * @title getColumnMetaData
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:42
     */
    private List<ColumnMeta> getColumnMetaData(DatabaseMeta databaseMeta, Connection connection, String catalog,
                                               String schema,
                                               String tableName) {
        List<ColumnMeta> columnMetaDatas;
        try {
            columnMetaDatas = Lists.newArrayList();
            if (databaseMeta == null) {
                databaseMeta = databaseMetaReader.getDatabaseMetaData(connection);
            }
            ColumnParserType columnParserType = databaseMeta.getDbType().getColumnParserType();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(catalog, schema, tableName, null);
            ResultSetMetaData columnMetadata = columns.getMetaData();
            Map<String, Object> rawMetadata = Maps.newHashMap();
            ColumnParser columnParser = CF.get(columnParserType);
            while (columns.next()) {
                ColumnMeta columnMeta = new ColumnMeta();
                for (int i = 0; i < columnMetadata.getColumnCount(); i++) {
                    String col_name = columnMetadata.getColumnName(i + 1);
                    Object value = columns.getObject(col_name);
                    rawMetadata.put(col_name, value);
                    processMetadata(columnMeta, col_name, value);
                }
                columnMeta.setRawMetadatas(rawMetadata);
                Class<?> javaClass = columnParser.calJavaClass(columnMeta.getDataType());
                columnMeta.setJavaClass(javaClass);
                columnMetaDatas.add(columnMeta);
            }
        } catch (Exception e) {
            throw new JdbcException("getColumnMetaData error", e);
        }

        return columnMetaDatas;

    }

    /**
     * @param columnMeta columnMeta
     * @param columName  columName
     * @param value      value
     * @return void
     * @title processMetadata
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:43
     */
    private void processMetadata(ColumnMeta columnMeta, String columName, Object value) {
        switch (columName) {
            case "TABLE_NAME":
                columnMeta.setTableName(ObjectValues.getString(value));
                break;
            case "COLUMN_NAME":
                columnMeta.setColumnName(ObjectValues.getString(value));
                break;

            case "DATA_TYPE":
                columnMeta.setDataType(ObjectValues.getInt(value, Integer.MIN_VALUE));
                try {
                    JDBCType jdbcType = JDBCType.valueOf(columnMeta.getDataType());
                    columnMeta.setJdbcType(jdbcType);
                } catch (Exception e) {
                    log.error("jdbc type error", e);
                }
                break;
            case "TYPE_NAME":
                columnMeta.setTypeName(ObjectValues.getString(value));
                break;
            case "COLUMN_SIZE":
                columnMeta.setColumnSize(ObjectValues.getInt(value));
                break;
            case "DECIMAL_DIGITS":
                columnMeta.setDecimalDigits(ObjectValues.getInt(value));
                break;

            case "IS_AUTOINCREMENT":
                columnMeta.setAutoIncrement(ObjectValues.getBoolean(value));
                break;
            case "NULLABLE":
                columnMeta.setNullable(ObjectValues.getBoolean(value));
                break;
            case "REMARKS":
                columnMeta.setRemarks(ObjectValues.getString(value));
                break;
        }

    }

}





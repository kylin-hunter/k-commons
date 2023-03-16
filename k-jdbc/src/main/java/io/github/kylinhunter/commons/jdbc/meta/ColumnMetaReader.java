package io.github.kylinhunter.commons.jdbc.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;

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
    private final DatabaseMetaReader databaseMetaReader;

    /**
     * @param catalog   catalog
     * @param tableName tableName
     * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
     * @title getColumnMetaData
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:42
     */
    public List<ColumnMeta> getColumnMetaData(String catalog, String tableName) {
        return getColumnMetaData(DataSourceUtils.getDefaultDataSource(), catalog, tableName);
    }

    /**
     * @param dataSource dataSource
     * @param catalog    catalog
     * @param tableName  tableName
     * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
     * @title getColumnMetaData
     * @description
     * @author BiJi'an
     * @date 2023-01-18 12:42
     */
    public List<ColumnMeta> getColumnMetaData(DataSourceEx dataSource, String catalog, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            catalog = catalog != null && catalog.length() > 0 ? catalog : null;
            return getColumnMetaData(connection, catalog, null, tableName);

        } catch (JdbcException e) {
            throw e;
        } catch (Exception e) {
            throw new JdbcException("getColumnMetaData error", e);
        }

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
    public List<ColumnMeta> getColumnMetaData(Connection connection, String catalog,
                                              String schema,
                                              String tableName) {
        List<ColumnMeta> columnMetaDatas;
        try {
            columnMetaDatas = ListUtils.newArrayList();
            DatabaseMeta databaseMeta = databaseMetaReader.getDatabaseMetaData(connection);
            ColumnParserType columnParserType = databaseMeta.getDbType().getColumnParserType();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(catalog, schema, tableName, null);
            ResultSetMetaData columnMetadata = columns.getMetaData();
            Map<String, Object> rawMetadata = MapUtils.newHashMap();
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
        //        log.info(columName + ":" + value);
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





package io.github.kylinhunter.commons.jdbc.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetaData;
import io.github.kylinhunter.commons.jdbc.meta.bean.DbMetaData;
import io.github.kylinhunter.commons.util.ObjectValues;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 **/
public class MetaDataService {

    public DbMetaData getDatabaseMetaData() throws SQLException {
        try (Connection connection = DataSourceUtils.getDefaultDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            DbMetaData dbMetaData = new DbMetaData();
            dbMetaData.setUrl(metaData.getURL());
            dbMetaData.setProductName(metaData.getDatabaseProductName());
            dbMetaData.setVersion(metaData.getDatabaseProductVersion());
            dbMetaData.setDriverName(metaData.getDriverName());
            return dbMetaData;
        }

    }

    public List<ColumnMetaData> getColumnMetaData(String schema, String tableName) throws SQLException {
        return getColumnMetaData(null, schema, tableName);
    }

    public List<ColumnMetaData> getColumnMetaData(String catalog, String schema, String tableName) throws SQLException {
        List<ColumnMetaData> columnMetaDatas = Lists.newArrayList();
        try (Connection connection = DataSourceUtils.getDefaultDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(catalog, schema, tableName, null);
            ResultSetMetaData columnMetadata = columns.getMetaData();
            Map<String, Object> rawMetadata = Maps.newHashMap();

            while (columns.next()) {
                ColumnMetaData columnMetaData = new ColumnMetaData();

                for (int i = 0; i < columnMetadata.getColumnCount(); i++) {
                    String col_name = columnMetadata.getColumnName(i + 1);
                    Object value = columns.getObject(col_name);
                    rawMetadata.put(col_name, value);
                    processMetadata(columnMetaData, col_name, value);
                }
                columnMetaData.setRawMetadatas(rawMetadata);
                columnMetaDatas.add(columnMetaData);
            }

            return columnMetaDatas;
        }

    }

    private void processMetadata(ColumnMetaData columnMetaData, String columName, Object value) {
        switch (columName) {
            case "TABLE_NAME":
                columnMetaData.setTableName(ObjectValues.getString(value));
                break;
            case "COLUMN_NAME":
                columnMetaData.setColumnName(ObjectValues.getString(value));
                break;

            case "DATA_TYPE":
                columnMetaData.setDataType(ObjectValues.getInt(value, Integer.MIN_VALUE));
                break;
            case "TYPE_NAME":
                columnMetaData.setTypeName(ObjectValues.getString(value));
                break;
            case "COLUMN_SIZE":
                columnMetaData.setColumnSize(ObjectValues.getInt(value));
                break;
            case "DECIMAL_DIGITS":
                columnMetaData.setDecimalDigits(ObjectValues.getInt(value));
                break;

            case "IS_AUTOINCREMENT":
                columnMetaData.setAutoIncrement(ObjectValues.getBoolean(value));
                break;
            case "NULLABLE":
                columnMetaData.setNullable(ObjectValues.getBoolean(value));
                break;
            case "REMARKS":
                columnMetaData.setRemarks(ObjectValues.getString(value));
                break;
        }

    }

    //    TABLE_CAT/kp
    //    TABLE_SCHEM/null
    //    TABLE_NAME/test_kylin_user
    //    COLUMN_NAME/id
    //    DATA_TYPE/-5
    //    TYPE_NAME/BIGINT
    //    COLUMN_SIZE/19
    //    BUFFER_LENGTH/65535
    //    DECIMAL_DIGITS/null
    //    NUM_PREC_RADIX/10
    //    NULLABLE/0
    //    REMARKS/主键id
    //    COLUMN_DEF/null
    //    SQL_DATA_TYPE/0
    //    SQL_DATETIME_SUB/0
    //    CHAR_OCTET_LENGTH/null
    //    ORDINAL_POSITION/1
    //    IS_NULLABLE/NO
    //    SCOPE_CATALOG/null
    //    SCOPE_SCHEMA/null
    //    SCOPE_TABLE/null
    //    SOURCE_DATA_TYPE/null
    //    IS_AUTOINCREMENT/YES
    //    IS_GENERATEDCOLUMN/NO
}





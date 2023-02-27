package io.github.kylinhunter.commons.generator.context;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.generator.config.bean.Database;
import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-26 19:04
 **/
public class FieldConvertor {

    /**
     * @param columnMeta columnMeta
     * @return java.lang.Class<?>
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2023-02-26 19:07
     */
    public static String convert(ColumnMeta columnMeta, Table table, Database database) {
        String columnType = table.getColumnType(columnMeta.getColumnName());
        if (!StringUtils.isEmpty(columnType)) {
            return columnType;
        }

        columnType = database.getSqlType(columnMeta.getTypeName().toLowerCase());
        if (!StringUtils.isEmpty(columnType)) {
            return columnType;
        }
        return columnMeta.getJavaClass().getName();

    }
}


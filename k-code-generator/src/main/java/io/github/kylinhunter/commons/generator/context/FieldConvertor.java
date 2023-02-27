package io.github.kylinhunter.commons.generator.context;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-26 19:04
 **/
public class FieldConvertor {

    /**
     * @param columnMeta
     * @return java.lang.Class<?>
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2023-02-26 19:07
     */
    public static String convert(ColumnMeta columnMeta, Table table, Map<String, String> sqlTypes) {
        String columnType = table.getColumnType(columnMeta.getColumnName());
        if (!StringUtils.isEmpty(columnType)) {

            return columnType;
        }

         columnType = sqlTypes.get(columnMeta.getTypeName().toLowerCase());
        if (!StringUtils.isEmpty(columnType)) {

            return columnType;
        }
        return columnMeta.getJavaClass().getName();



    }
}


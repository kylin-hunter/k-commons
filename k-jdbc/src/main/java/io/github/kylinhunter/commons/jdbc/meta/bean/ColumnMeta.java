package io.github.kylinhunter.commons.jdbc.meta.bean;

import java.sql.JDBCType;
import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 **/
@Data
@ToString(exclude = "rawMetadatas")
public class ColumnMeta {

    private Map<String, Object> rawMetadatas = Maps.newHashMap();
    private String tableName;
    private String columnName;

    private int dataType;
    private String typeName;
    private int columnSize;
    private int decimalDigits;

    private boolean autoIncrement;
    private boolean nullable;
    private String remarks;

    private JDBCType jdbcType;
    private Class<?> javaClass;

}

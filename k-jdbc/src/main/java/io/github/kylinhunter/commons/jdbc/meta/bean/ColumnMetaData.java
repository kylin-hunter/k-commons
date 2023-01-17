package io.github.kylinhunter.commons.jdbc.meta.bean;

import java.sql.JDBCType;
import java.util.Map;
import java.util.StringJoiner;

import com.google.common.collect.Maps;

import lombok.Data;
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 **/
@Data
@ToString(exclude="rawMetadatas")
public class ColumnMetaData {

    private Map<String, Object> rawMetadatas = Maps.newHashMap();
    private String tableName;
    private String columnName;

    private JDBCType jdbcType;
    private int dataType;
    private String typeName;
    private int columnSize;
    private int decimalDigits;

    private boolean autoIncrement;
    private boolean nullable;
    private String remarks;

    public void setDataType(int dataType) {
        this.dataType = dataType;
        this.jdbcType=JDBCType.valueOf(dataType);
    }


}

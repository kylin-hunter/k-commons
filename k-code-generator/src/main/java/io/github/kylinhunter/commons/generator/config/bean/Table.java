package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class Table {
    private String name;
    private List<String> skipColumns = Lists.newArrayList();
    private Map<String, String> columnTypes = Maps.newHashMap();

    /**
     * @param column column
     * @return java.lang.String
     * @title getColumnType
     * @description
     * @author BiJi'an
     * @date 2023-02-26 19:16
     */
    public String getColumnType(String column) {

        return columnTypes.get(column);

    }
}

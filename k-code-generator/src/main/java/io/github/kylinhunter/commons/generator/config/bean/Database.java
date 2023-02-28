package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-26 23:39
 **/
@Data
public class Database {
    protected String name;
    private Map<String, String> sqlTypes = Maps.newHashMap();

    /**
     * @param type type
     * @return java.lang.String
     * @title getSqlType
     * @description
     * @author BiJi'an
     * @date 2023-02-26 23:50
     */
    public String getSqlType(String type) {
        return sqlTypes.get(type);
    }
}

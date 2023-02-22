package io.github.kylinhunter.commons.jdbc.meta.bean;

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
public class TableMeta {
    private Map<String, Object> rawMetadatas = Maps.newHashMap();

    private String name;

    private String remarks;

}

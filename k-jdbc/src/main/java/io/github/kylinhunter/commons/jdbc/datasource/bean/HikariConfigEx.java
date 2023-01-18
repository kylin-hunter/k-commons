package io.github.kylinhunter.commons.jdbc.datasource.bean;

import org.apache.commons.lang3.StringUtils;

import com.zaxxer.hikari.HikariConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-18 11:01
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class HikariConfigEx extends HikariConfig {
    private int no;
    private String name;

    public void setName(String name) {
        if (!StringUtils.isEmpty(name)) {
            this.name = name;
        } else {
            this.name = "datasource-" + no;
        }
    }
}
package com.kylinhunter.plat.commons.util.name;

import java.io.Serializable;

import lombok.Data;

/**
 * @description
 * @author BiJi'an
 * @date   2022-01-21 19:43
 **/
@Data
public class NamePair implements Serializable {
    private String camel; /*驼峰模式*/
    private String snake; /*后端数据库使用下划线（蛇形）模式*/
}

package io.github.kylinhunter.commons.generator.context.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-17 16:13
 **/
@Data
public class Column {
    private final String name;
    private final Class<?> clazz;
}

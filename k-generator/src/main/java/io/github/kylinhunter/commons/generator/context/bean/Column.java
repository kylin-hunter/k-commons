package io.github.kylinhunter.commons.generator.context.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-17 16:13
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {
    private String name;
    private Class<?> clazz;
}

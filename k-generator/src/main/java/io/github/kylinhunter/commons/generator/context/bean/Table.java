package io.github.kylinhunter.commons.generator.context.bean;

import java.util.List;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-17 22:27
 **/
@Data
public class Table {
    private String name;
    private List<Column> columns;
}

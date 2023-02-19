package io.github.kylinhunter.commons.generator.context.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-17 22:27
 **/
@Data
@NoArgsConstructor
public class TableInfo {
    private String name;
    private String remarks;
    private List<Column> columns;
}

package io.github.kylinhunter.commons.jdbc.datasource.bean;

import java.util.List;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 20:09
 **/
@Data
public class DataSourceConfig {
    private List<DataSourceInfo> datasources;
}

package io.github.kylinhunter.commons.jdbc.datasource;

import java.io.Closeable;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-18 00:39
 */
public interface DataSourceEx extends DataSource, Closeable, DSAccessor {}

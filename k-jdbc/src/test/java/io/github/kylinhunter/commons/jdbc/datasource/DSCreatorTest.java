package io.github.kylinhunter.commons.jdbc.datasource;

import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.clazz.debug.DebugOption;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DSCreatorTest {

  @Test
  void create() {
    Class<? extends DataSourceEx> clazz = DSCreator
        .create(HikariDataSource.class, DebugOption.INSTANCE);
    DataSourceEx dataSourceEx = ObjectCreator.create(clazz);

    dataSourceEx.setDsName("aaa");
    Assertions.assertEquals("aaa", dataSourceEx.getDsName());

    dataSourceEx.setDsNo(999);
    Assertions.assertEquals(999, dataSourceEx.getDsNo());
  }
}
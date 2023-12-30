package io.github.kylinhunter.commons.jdbc.datasource;

import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.init.DebugOption;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DSCreatorTest {

  @Test
  void create() {
    Class<? extends ExDataSource> clazz =
        DSCreator.create(HikariDataSource.class, DebugOption.INSTANCE);
    ExDataSource exDataSource = ObjectCreator.create(clazz);

    exDataSource.setDsName("aaa");
    Assertions.assertEquals("aaa", exDataSource.getDsName());

    exDataSource.setDsNo(999);
    Assertions.assertEquals(999, exDataSource.getDsNo());
  }
}

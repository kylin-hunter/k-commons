package io.github.kylinhunter.commons.jdbc.bytebuddy.test;

import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.jdbc.datasource.DSAccessor;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default;
import net.bytebuddy.implementation.FieldAccessor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-26 11:04
 */
public class Test {

  public static void main(String[] args)
      throws IOException, IllegalAccessException, InstantiationException, SQLException {

    File tmpDir = UserDirUtils.getTmpDir("bytebuddy-test");
    DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
        .subclass(HikariDataSource.class)
        .defineField("dsNo", int.class, Visibility.PRIVATE)
        .defineField("dsName", String.class, Visibility.PRIVATE)
        .implement(DSAccessor.class)
        .intercept(FieldAccessor.ofBeanProperty())
        .make();

    dynamicType.saveIn(tmpDir);
    Class<?> loaded = dynamicType.load(Test.class.getClassLoader(), Default.WRAPPER)
        .getLoaded();
    DataSource o = (DataSource) loaded.newInstance();
  }

}

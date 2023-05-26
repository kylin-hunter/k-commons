package io.github.kylinhunter.commons.jdbc.datasource;

import io.github.kylinhunter.commons.clazz.debug.DebugOption;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-27 00:00
 */
@Slf4j
public class DSCreator {

  /**
   * @param clazz clazz
   * @return java.lang.Class<? extends T>
   * @title create
   * @description
   * @author BiJi'an
   * @date 2023-05-27 00:26
   */
  public static <T extends DataSource> Class<? extends DataSourceEx> create(Class<T> clazz) {
    return create(clazz, null);
  }


  /**
   * @param clazz clazz
   * @return java.lang.Class<? extends T>
   * @title create
   * @description
   * @author BiJi'an
   * @date 2023-05-27 00:15
   */
  public static <T extends DataSource> Class<? extends DataSourceEx> create(Class<T> clazz,
      DebugOption debugOption) {
    DynamicType.Unloaded<T> dynamicType = new ByteBuddy()
        .subclass(clazz)
        .implement(DataSourceEx.class)
        .defineField("dsNo", int.class, Visibility.PRIVATE)
        .defineField("dsName", String.class, Visibility.PRIVATE)
        .implement(DSNameAccessor.class, DSNoAccessor.class)
        .intercept(FieldAccessor.ofBeanProperty())
        .make();
    processDebug(dynamicType, debugOption);
    return (Class<? extends DataSourceEx>) dynamicType
        .load(DSCreator.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
        .getLoaded();

  }

  /**
   * @param debugOption debugOption
   * @return void
   * @title process
   * @description
   * @author BiJi'an
   * @date 2023-05-27 01:15
   */
  private static void processDebug(DynamicType.Unloaded<?> dynamicType, DebugOption debugOption) {
    try {
      if (debugOption != null) {
        dynamicType.saveIn(debugOption.getSaveDir());
      }
    } catch (Exception e) {
      log.error("processDebug error", e);
    }
  }
}

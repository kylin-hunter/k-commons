package io.github.kylinhunter.commons.jdbc.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.jdbc.datasource.bean.HikariConfigEx;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import org.apache.commons.io.IOUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 00:34
 */
public class DataSourceUtils {

  private static final HikariConfigExParser hikariConfigExParser = new HikariConfigExParser();
  @Getter
  private static DataSourceEx defaultDataSource;
  private static final Map<Integer, DataSourceEx> ID_DATA_SOURCES = MapUtils.newHashMap();
  private static final Map<String, DataSourceEx> NAME_DATA_SOURCES = MapUtils.newHashMap();

  static {
    init(null);
  }

  /**
   * @param path path
   * @return void
   * @title load
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public static synchronized void init(String path) {
    closeAll();
    List<HikariConfigEx> dataSources = hikariConfigExParser.load(path);
    if (CollectionUtils.isEmpty(dataSources)) {
      throw new InitException(" can't find datasource config");
    }
    for (int i = 0; i < dataSources.size(); i++) {
      HikariConfigEx hikariConfigEx = dataSources.get(i);
      int no = hikariConfigEx.getNo();
      String name = hikariConfigEx.getName();
      HikariDataSource hikariDataSource = new HikariDataSource(hikariConfigEx);
      Class<? extends DataSourceEx> clazz = DSCreator.create(HikariDataSource.class);

      DataSourceEx dataSourceEx = ObjectCreator
          .create(clazz, new Class[]{HikariConfig.class}, new Object[]{hikariConfigEx});
      if (defaultDataSource == null) {
        defaultDataSource = dataSourceEx;
      }
      ID_DATA_SOURCES.put(no, dataSourceEx);
      NAME_DATA_SOURCES.put(name, dataSourceEx);
    }
  }

  DynamicType.Unloaded<?> dynamicType = new ByteBuddy()

      .subclass(HikariDataSource.class)
      .defineField("no", int.class, Visibility.PRIVATE)
      .defineField("name", String.class, Visibility.PRIVATE)
      .implement(DSNameAccessor.class, DSNoAccessor.class)
      .intercept(FieldAccessor.ofBeanProperty())
      .make();

  /**
   * @return void
   * @title closeAll
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  private static void closeAll() {
    for (DataSourceEx dataSourceEx : ID_DATA_SOURCES.values()) {
      IOUtils.closeQuietly(dataSourceEx);
    }
  }

  /**
   * @param no no
   * @return io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx
   * @title getByNo
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public static DataSourceEx getByNo(int no) {
    return ID_DATA_SOURCES.get(no);
  }

  /**
   * @param name name
   * @return io.github.kylinhunter.commons.jdbc.datasource.DataSourceEx
   * @title getByName
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:25
   */
  public static DataSourceEx getByName(String name) {
    return NAME_DATA_SOURCES.get(name);
  }

}

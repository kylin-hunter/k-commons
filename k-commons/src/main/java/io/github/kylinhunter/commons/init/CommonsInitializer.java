package io.github.kylinhunter.commons.init;

import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import io.github.kylinhunter.commons.sys.KConst;
import java.util.Set;
import java.util.logging.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-16 10:57
 */
public class CommonsInitializer {

  private static final Logger log = Logger.getLogger(CommonsInitializer.class.toString());
  private static final CommonsInitializer commonsInitializer = new CommonsInitializer();

  private final Set<String> allPackages = SetUtils.newHashSet();
  private DebugOption debugOption;

  public CommonsInitializer() {
    this.scanPackage(KConst.K_BASE_PACKAGE);
  }

  /**
   * @return io.github.kylinhunter.commons.init.CommonsInitializer
   * @title custom
   * @description custom
   * @author BiJi'an
   * @date 2023-06-19 23:27
   */
  public static CommonsInitializer custom() {
    return commonsInitializer;
  }

  /**
   * @param pkg pkg
   * @title scanPackage
   * @description scanPackage
   * @author BiJi'an
   * @date 2023-06-19 23:27
   */
  public void scanPackage(String pkg) {
    allPackages.add(pkg);
  }


  /**
   * @title debug
   * @description debug
   * @author BiJi'an
   * @date 2023-06-19 23:28
   */
  public void debug(boolean debug) {
    if (debug) {
      this.debugOption(DebugOption.INSTANCE);
    }
  }

  /**
   * @param debugOption debugOption
   * @title debugOption
   * @description debugOption
   * @author BiJi'an
   * @date 2023-06-19 23:33
   */
  public void debugOption(DebugOption debugOption) {
    this.debugOption = debugOption;
  }

  /**
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-06-19 23:28
   */
  public void init() {

    Reflections reflections = new Reflections(allPackages, Scanners.SubTypes,
        Scanners.TypesAnnotated);
    Set<Class<? extends AbstractInitializer>> initializers = reflections.getSubTypesOf(
        AbstractInitializer.class);

    for (Class<? extends AbstractInitializer> initializerClazz : initializers) {
      log.info("init " + initializerClazz.getName());
      Initializer initializer = ObjectCreator.create(initializerClazz);
      initializer.setDebugOption(this.debugOption);
      initializer.initialize(allPackages);
    }

  }
}
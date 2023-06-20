package io.github.kylinhunter.commons.init;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import java.util.Map;
import java.util.Set;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023/6/19
 **/
public abstract class AbstractInitializer implements Initializer {

  @Setter
  protected DebugOption debugOption;

  @Setter
  protected static volatile Map<Class<?>, Boolean> INITIALIZED_TAGS = MapUtils.newHashMap();

  @Setter
  protected ClassScanner classScanner;


  public AbstractInitializer(Set<String> pkgs) {
    this.classScanner = new ClassScanner(pkgs);
  }

  public AbstractInitializer(ClassScanner classScanner) {
    this.classScanner = classScanner;
  }

  /**
   * @title initialize
   * @description initialize
   * @author BiJi'an
   * @date 2023-06-21 00:57
   */
  public void initialize() throws InitException {
    Boolean initialized = INITIALIZED_TAGS.get(this.getClass());
    if (initialized == null || !initialized) {
      INITIALIZED_TAGS.put(this.getClass(), true);
      init();
    }
  }

  public abstract void init() throws InitException;
}

package io.github.kylinhunter.commons.component;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.reflections.ReflectionUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 */
class CMethodManager {
  private final Map<Class<?>, List<CMethod>> cmethodsMap = MapUtils.newHashMap();
  @Getter private List<CMethod> cmethods = ListUtils.newArrayList();

  private final CompManager compManager;
  private final CompTools compTools;
  private final CMethodDepCalculator methodDepCalculator;
  @Getter private final Set<Class<?>> compClasses = SetUtils.newHashSet();

  public CMethodManager(CompManager compManager) {
    this.compManager = compManager;
    this.compTools = compManager.compTools;
    this.methodDepCalculator = new CMethodDepCalculator(this);
  }

  public void clean() {
    cmethodsMap.clear();
    cmethods.clear();
    compClasses.clear();
  }

  /**
   * @return void
   * @title calComponents
   * @description
   * @author BiJi'an
   * @date 2023-01-20 00:27
   */
  public void calculate() {
    this.clean();
    Set<Class<?>> ccClasses = compTools.getClassesByAnnotatedWith(CC.class);
    for (Class<?> ccClazz : ccClasses) {
      calculate(ccClazz);
    }
    this.cmethods = methodDepCalculator.calculate(this.cmethods);
  }

  /**
   * @param compClazz compClazz
   * @return void
   * @title calculate
   * @description
   * @author BiJi'an
   * @date 2023-02-11 23:18
   */
  @SuppressWarnings("unchecked")
  private void calculate(Class<?> compClazz) {
    Object ccObject = ObjectCreator.create(compClazz);
    compManager.register(compClazz, ccObject);
    Set<Method> methods = ReflectionUtils.getAllMethods(compClazz);
    for (Method method : methods) {
      C c = method.getAnnotation(C.class);
      if (c != null) {
        CMethod cmethod = new CMethod(method, ccObject, c);
        this.compClasses.add(method.getReturnType());
        this.cmethods.add(cmethod);
        registerAll(method.getReturnType(), cmethod);
      }
    }
  }

  /**
   * @param compClazz compClazz
   * @param cmethod cmethod
   * @return void
   * @title registerAll
   * @description
   * @author BiJi'an
   * @date 2023-02-11 23:08
   */
  private void registerAll(Class<?> compClazz, CMethod cmethod) {
    this.register(compClazz, cmethod);
    Set<Class<?>> interfaces = compTools.getAllInterface(compClazz);
    for (Class<?> interfaceClazz : interfaces) {
      this.register(interfaceClazz, cmethod);
    }
  }

  /**
   * @param clazz clazz
   * @param cmethod cmethod
   * @return void
   * @title register
   * @description
   * @author BiJi'an
   * @date 2023-02-11 22:45
   */
  private void register(Class<?> clazz, CMethod cmethod) {
    cmethodsMap.compute(
        clazz,
        (k, v) -> {
          if (v == null) {
            v = ListUtils.newArrayList();
          }
          if (!v.contains(cmethod)) {
            if (cmethod.isPrimary()) {
              v.add(0, cmethod);
            } else {
              v.add(cmethod);
            }
          }
          return v;
        });
  }

  /**
   * @param compClazz compClazz
   * @return io.github.kylinhunter.commons.component.CMethod
   * @title getFirst
   * @description
   * @author BiJi'an
   * @date 2023-02-11 22:43
   */
  public CMethod get(Class<?> compClazz) {
    return get(compClazz, false);
  }

  /**
   * @param compClazz compClazz
   * @param required required
   * @return io.github.kylinhunter.commons.component.CConstructor
   * @title getFirst
   * @description
   * @author BiJi'an
   * @date 2023-02-11 22:10
   */
  public CMethod get(Class<?> compClazz, boolean required) {
    List<CMethod> methods = cmethodsMap.get(compClazz);
    if (methods != null && methods.size() > 0) {
      return methods.get(0);
    }
    if (required) {
      throw new InitException(" no cmethods ");
    }
    return null;
  }

  /**
   * @param compClazz compClazz
   * @return java.util.List<io.github.kylinhunter.commons.component.CMethod>
   * @title getAll
   * @description
   * @author BiJi'an
   * @date 2023-02-11 23:21
   */
  public List<CMethod> getAll(Class<?> compClazz) {
    List<CMethod> cConstructors = cmethodsMap.get(compClazz);
    if (cConstructors != null) {
      return cConstructors;
    } else {
      return Collections.emptyList();
    }
  }
}

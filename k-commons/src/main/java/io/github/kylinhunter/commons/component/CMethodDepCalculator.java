package io.github.kylinhunter.commons.component;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 02:19
 */
public class CMethodDepCalculator {
  private final Map<CMethod, Set<CMethod>> dependencies = MapUtils.newHashMap();
  private final CMethodManager methodManager;

  public CMethodDepCalculator(CMethodManager methodManager) {
    this.methodManager = methodManager;
  }

  /**
   * @return void
   * @title clear
   * @description
   * @author BiJi'an
   * @date 2023-01-20 01:06
   */
  public void clean() {
    dependencies.clear();
  }

  /**
   * @return void
   * @title calDependencies
   * @description
   * @author BiJi'an
   * @date 2023-01-19 23:25
   */
  public List<CMethod> calculate(List<CMethod> cmethods) {
    this.clean();
    for (CMethod cmethod : methodManager.getCmethods()) {
      this.calculate(cmethod);
    }
    return cmethods.stream()
        .sorted(Comparator.comparingInt(CMethod::getDepLevel))
        .collect(Collectors.toList());
  }

  /**
   * @param cmethod cmethod
   * @return void
   * @title calDependencies
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  private void calculate(CMethod cmethod) {
    calculate(cmethod, SetUtils.newHashSet(cmethod));
    cmethod.setDepLevel(dependencies.get(cmethod).size());
  }

  /**
   * @param oriCmethod oriCmethod
   * @return void
   * @title calDependencies
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:21
   */
  private void calculate(CMethod oriCmethod, Set<CMethod> depCMethods) {
    if (depCMethods.size() <= 0) {
      return;
    }

    for (CMethod cmethod : depCMethods) {
      dependencies.compute(
          oriCmethod,
          (k, v) -> {
            if (v == null) {
              v = SetUtils.newHashSet();
            }
            v.add(cmethod);
            return v;
          });

      Method method = cmethod.getMethod();
      if (method.getParameterCount() > 0) {
        Set<CMethod> tmpCMethods = SetUtils.newHashSet();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
          Class<?> curParametorClass = parameterTypes[i];
          if (Collection.class.isAssignableFrom(curParametorClass)) {
            Type type = genericParameterTypes[i];
            Class<?>[] actualTypeArgumentClasses = GenericTypeUtils.getActualTypeArguments(type);
            for (Class<?> actualTypeArgumentClass : actualTypeArgumentClasses) {
              Collection<CMethod> all = methodManager.getAll(actualTypeArgumentClass);
              tmpCMethods.addAll(all);
            }
          } else {
            tmpCMethods.add(methodManager.get(curParametorClass, true));
          }
          calculate(oriCmethod, tmpCMethods);
        }
      }
    }
  }
}

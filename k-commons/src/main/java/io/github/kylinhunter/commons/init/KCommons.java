/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.init;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.reflect.Constructors;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import io.github.kylinhunter.commons.sys.KConst;
import io.github.kylinhunter.commons.sys.KGenerated;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-16 10:57
 */
@KGenerated
public class KCommons {

  private static final Logger log = Logger.getLogger(KCommons.class.toString());
  private static final KCommons K_COMMONS = new KCommons();

  private final Set<String> allPackages = SetUtils.newHashSet();
  private DebugOption debugOption;

  public KCommons() {
    this.scanPackage(KConst.K_BASE_PACKAGE);
  }

  /**
   * @return io.github.kylinhunter.commons.init.CommonsInitializer
   * @title custom
   * @description custom
   * @author BiJi'an
   * @date 2023-06-19 23:27
   */
  public static KCommons custom() {
    return K_COMMONS;
  }

  /**
   * @param pkg pkg
   * @title scanPackage
   * @description scanPackage
   * @author BiJi'an
   * @date 2023-06-19 23:27
   */
  public KCommons scanPackage(String pkg) {
    allPackages.add(pkg);
    return this;
  }

  /**
   * @param debug debug
   * @return io.github.kylinhunter.commons.init.KCommons
   * @title debug
   * @description debug
   * @author BiJi'an
   * @date 2023-06-21 01:59
   */
  public KCommons debug(boolean debug) {
    if (debug) {
      this.debugOption(DebugOption.INSTANCE);
    }
    return this;
  }

  /**
   * @param debugOption debugOption
   * @title debugOption
   * @description debugOption
   * @author BiJi'an
   * @date 2023-06-19 23:33
   */
  public KCommons debugOption(DebugOption debugOption) {
    this.debugOption = debugOption;
    return this;
  }

  /**
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-06-19 23:28
   */
  public void init() {
    ClassScanner classScanner = new ClassScanner(allPackages);

    Map<Integer, List<Class<? extends Initializer>>> treeMap = MapUtils.newTreeMap();
    for (Class<? extends Initializer> clazz :
        classScanner.getSubTypesOf(AbstractInitializer.class)) {
      Order order = clazz.getAnnotation(Order.class);
      int orderNum = order != null ? order.value() : 0;
      treeMap.compute(
          orderNum,
          (k, v) -> {
            if (v == null) {
              v = ListUtils.newArrayList();
            }
            v.add(clazz);
            return v;
          });
    }

    treeMap.forEach(
        (k, clazzes) -> {
          for (Class<? extends Initializer> clazz : clazzes) {
            log.info("init " + clazz.getName());
            Constructor<? extends Initializer> constructor =
                Constructors.getConstructor(clazz, ClassScanner.class);
            Initializer initializer = ObjectCreator.create(constructor, classScanner);
            initializer.setDebugOption(debugOption);
            initializer.initialize();
          }
        });
  }
}

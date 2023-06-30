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
package io.github.kylinhunter.commons.component;

import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.init.ClassScanner;
import io.github.kylinhunter.commons.sys.KConst;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-22 01:48
 */
public class CF {

  private static CompManager COMP_MANAGER;

  static {
    new CFInitializer(SetUtils.newHashSet(KConst.K_BASE_PACKAGE)).initialize();
  }

  /**
   * @title init
   * @description
   * @author BiJi'an
   * @date 2022-11-08 21:38
   */
  public static void init(ClassScanner classScanner) {
    COMP_MANAGER = new CompManager(classScanner);
    COMP_MANAGER.init();
  }

  /**
   * @param ct ct
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-12-03 20:08
   */
  public static <T> T get(CT<T> ct) {
    return get(ct.getClazz(), true);
  }

  /**
   * @param clazz clazz
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  public static <T, S extends T> T get(Class<S> clazz) {
    return get(clazz, true);
  }

  /**
   * @param compClazz clazz
   * @param required required
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  public static <T, S extends T> T get(Class<S> compClazz, boolean required) {
    return COMP_MANAGER.get(compClazz, required);
  }

  /**
   * @param clazz clazz
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  public static <T> List<T> getAll(Class<T> clazz) {
    return COMP_MANAGER.getAll(clazz, true);
  }

  /**
   * @param compClazz clazz
   * @param required required
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  public static <T> List<T> getAll(Class<T> compClazz, boolean required) {
    return COMP_MANAGER.getAll(compClazz, required);
  }
}

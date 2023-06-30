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
package io.github.kylinhunter.commons.util;

import io.github.kylinhunter.commons.collections.SetUtils;
import java.util.Set;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 14:18
 */
public class OnceRunner {

  private static final Set<String> runNames = SetUtils.newHashSet();

  public static void run(Class<?> clazz, Runner r) {
    run(clazz.getName(), r);
  }

  /**
   * @param name name
   * @param r r
   * @return void
   * @title run
   * @description
   * @author BiJi'an
   * @date 2023-03-11 23:07
   */
  public static void run(String name, Runner r) {
    if (r != null) {
      if (!runNames.contains(name)) {
        synchronized (OnceRunner.class) {
          if (!runNames.contains(name)) {
            r.run();
            runNames.add(name);
          }
        }
      }
    }
  }

  @FunctionalInterface
  public static interface Runner {
    void run();
  }
}

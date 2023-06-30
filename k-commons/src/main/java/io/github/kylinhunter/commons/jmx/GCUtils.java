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
package io.github.kylinhunter.commons.jmx;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:47
 */
public class GCUtils {

  /**
   * @return void
   * @title printGCInfo
   * @description
   * @author BiJi'an
   * @date 2023-03-11 15:00
   */
  public static void printGCInfo() {
    List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
    for (GarbageCollectorMXBean garbage : garbages) {
      String info =
          String.format(
              "name: %s\t count:%s\t took:%s\t pool name:%s",
              garbage.getName(),
              garbage.getCollectionCount(),
              garbage.getCollectionTime(),
              Arrays.deepToString(garbage.getMemoryPoolNames()));
      System.out.println(info);
    }
  }
}

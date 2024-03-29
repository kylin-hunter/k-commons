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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:47
 */
public class MemoryUtils {
  private static final long MB = 1048576L;

  /**
   * @return void
   * @title printHeapMemory
   * @description
   * @author BiJi'an
   * @date 2023-03-11 15:00
   */
  public static void printHeapMemory() {
    MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
    MemoryUsage headMemory = memory.getHeapMemoryUsage();

    String info =
        String.format(
            "%ninit: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s%n",
            headMemory.getInit() / MB + "MB",
            headMemory.getMax() / MB + "MB",
            headMemory.getUsed() / MB + "MB",
            headMemory.getCommitted() / MB + "MB",
            headMemory.getUsed() * 100 / headMemory.getCommitted() + "%");

    System.out.print(info);
  }

  /**
   * @return void
   * @title printNonHeapMemory
   * @description
   * @author BiJi'an
   * @date 2023-03-11 15:00
   */
  public static void printNonHeapMemory() {
    MemoryMXBean memory = ManagementFactory.getMemoryMXBean();

    MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();

    String info =
        String.format(
            "init: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s%n",
            nonheadMemory.getInit() / MB + "MB",
            nonheadMemory.getMax() / MB + "MB",
            nonheadMemory.getUsed() / MB + "MB",
            nonheadMemory.getCommitted() / MB + "MB",
            nonheadMemory.getUsed() * 100 / nonheadMemory.getCommitted() + "%");
    System.out.println(info);
  }
}

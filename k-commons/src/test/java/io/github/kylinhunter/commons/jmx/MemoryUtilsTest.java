package io.github.kylinhunter.commons.jmx;

import org.junit.jupiter.api.Test;

class MemoryUtilsTest {

  @Test
  void test() {
    MemoryUtils.printHeapMemory();
    MemoryUtils.printNonHeapMemory();
  }
}

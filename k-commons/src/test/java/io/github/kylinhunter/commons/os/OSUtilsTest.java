package io.github.kylinhunter.commons.os;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OSUtilsTest {

  @Test
  void os() {

    String property = System.getProperty("os.name").toLowerCase();
    OS os = OSUtils.os();
    System.out.println(property + ":" + os);
    if (property.contains("win")) {
      Assertions.assertEquals(OS.WINDOWS, os);

    } else if (property.contains("mac")) {
      Assertions.assertEquals(OS.MAC, os);


    }


  }
}
package io.github.kylinhunter.commons.reflect.test;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-03 20:51
 */
@Getter
@Setter
public class TestClass {
  private int a;
  private int b;

  public TestClass(int a, int b) {
    this.a = a;
    this.b = b;
  }

  public void println() {
    System.out.println(a + ":" + b);
  }
}

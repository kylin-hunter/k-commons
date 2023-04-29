package io.github.kylinhunter.commons.component;

import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 02:14
 */
@C(order = 3)
@RequiredArgsConstructor
public class A2 implements I {
  private final A1 a1;

  public void println() {
    System.out.println(this.getClass().getSimpleName() + "/" + a1.getClass().getSimpleName());
  }
}

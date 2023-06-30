package io.github.kylinhunter.commons.component;

import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 02:14
 */
@RequiredArgsConstructor
public class B2 implements I {
  private final B1 b1;

  public void println() {
    System.out.println(this.getClass().getSimpleName() + "/" + b1.getClass().getSimpleName());
  }
}

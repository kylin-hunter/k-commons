package io.github.kylinhunter.commons.reflect.test;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-10 19:36
 */
@Getter
public class ReflectBeanFather extends ReflectBeanGrandFather {

  private int f1;
  private int f2;

  public ReflectBeanFather() {
  }

  public ReflectBeanFather(int f1, int f2) {
    this.f1 = f1;
    this.f2 = f2;
  }
}

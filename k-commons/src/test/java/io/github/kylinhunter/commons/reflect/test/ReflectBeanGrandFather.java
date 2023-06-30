package io.github.kylinhunter.commons.reflect.test;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-10 19:36
 */
@Getter
public class ReflectBeanGrandFather implements ReflectInterface4 {

  private int gf1;
  private int gf2;

  public ReflectBeanGrandFather() {
  }

  public ReflectBeanGrandFather(int gf1, int gf2) {
    this.gf1 = gf1;
    this.gf2 = gf2;
  }

  @Override
  public void interface4() {
  }
}

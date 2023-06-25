package io.github.kylinhunter.commons.reflect.test;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-10 19:36
 */
@Getter
public class ReflectBeanChild extends ReflectBeanFather
    implements ReflectInterface1, ReflectInterface3 {

  private int f1;
  private int c1;
  private int c2;


  public ReflectBeanChild() {

  }

  public ReflectBeanChild(int f1, int c1, int c2) {
    this.f1 = f1;
    this.c1 = c1;
    this.c2 = c2;

  }

  @Override
  public void interface1() {
  }

  @Override
  public void interface2() {
  }

  @Override
  public void interface3() {
  }
}

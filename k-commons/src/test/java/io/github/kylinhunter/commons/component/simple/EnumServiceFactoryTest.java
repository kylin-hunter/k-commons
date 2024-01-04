package io.github.kylinhunter.commons.component.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnumServiceFactoryTest {

  @Test
  void init() {

    EnumServiceFactory enumServiceFactory = new EnumServiceFactory();
    enumServiceFactory.init(ServiceService.class);

    A service1 = enumServiceFactory.getService(ServiceService.S1);
    Assertions.assertEquals("A1", service1.call());

    A service2 = enumServiceFactory.getService(ServiceService.S2);
    Assertions.assertEquals("A2", service2.call());


  }


  private enum ServiceService implements EnumService<A> {
    S1(A1.class),
    S2(A2.class);

    private final Class<? extends A> clazz;

    ServiceService(Class<? extends A> clazz) {
      this.clazz = clazz;
    }

    @Override
    public Class<? extends A> getClazz() {
      return clazz;
    }
  }

  public interface A {

    String call();
  }

  public static class A1 implements A {


    @Override
    public String call() {
      return "A1";
    }
  }

  public static class A2 implements A {


    @Override
    public String call() {
      return "A2";
    }
  }


}
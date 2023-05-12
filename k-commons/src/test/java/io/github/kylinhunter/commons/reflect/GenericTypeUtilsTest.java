package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.reflect.bean.ActualType;
import io.github.kylinhunter.commons.reflect.test.generic.TestClassForGeneric;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenericTypeUtilsTest {

  @Test
  void test() throws NoSuchMethodException {

    System.out.println("===>test super type");
    ActualType actualType = GenericTypeUtils.getSuperClassActualType(TestClassForGeneric.class);
    System.out.println(actualType);
    Assertions.assertEquals(Integer.class, actualType.getType(0));
    Assertions.assertEquals(String.class, actualType.getType(1));

    System.out.println("===>test super type[0]");
    Class<?> actualTypeClass =
        GenericTypeUtils.getSuperClassActualType(TestClassForGeneric.class, 0);
    System.out.println(actualTypeClass);
    Assertions.assertEquals(Integer.class, actualTypeClass);

    System.out.println("===>test  interfaces");
    ActualType[] interfacesActualTypes =
        GenericTypeUtils.getInterfaceActualTypes(TestClassForGeneric.class);
    for (int i = 0; i < interfacesActualTypes.length; i++) {
      ActualType tmpActualType = interfacesActualTypes[i];
      System.out.println(tmpActualType);

      if (i == 0) {
        Assertions.assertEquals(String.class, tmpActualType.getType(0));
        Assertions.assertEquals(Integer.class, tmpActualType.getType(1));
      } else if (i == 1) {
        Assertions.assertEquals(Integer.class, tmpActualType.getType(0));
        Assertions.assertEquals(String.class, tmpActualType.getType(1));
      }
    }

    System.out.println("===>test  interfaces[0]");
    ActualType actualTypes = GenericTypeUtils.getInterfaceActualType(TestClassForGeneric.class, 0);
    System.out.println(actualTypes);
    Assertions.assertEquals(String.class, actualTypes.getType(0));
    Assertions.assertEquals(Integer.class, actualTypes.getType(1));

    System.out.println("===>test  interfaces[1][1]");
    actualTypeClass = GenericTypeUtils.getInterfaceActualType(TestClassForGeneric.class, 1, 1);
    System.out.println(actualTypeClass);
    Assertions.assertEquals(String.class, actualTypeClass);

    System.out.println("===>test method return type");
    Method testMethod = TestClassForGeneric.class.getMethod("test", List.class, Map.class);
    ActualType methodReturnActualType = GenericTypeUtils.getMethodReturnActualType(testMethod);
    System.out.println(methodReturnActualType);
    Assertions.assertEquals(String.class, methodReturnActualType.getType(0));
    Assertions.assertEquals(Integer.class, methodReturnActualType.getType(1));

    System.out.println("===>test method return type[1]");
    actualTypeClass = GenericTypeUtils.getMethodReturnActualType(testMethod, 1);
    System.out.println(actualTypeClass);
    Assertions.assertEquals(Integer.class, actualTypeClass);

    System.out.println("===>test method parameter type");

    interfacesActualTypes = GenericTypeUtils.getMethodParamterActualType(testMethod);
    for (int i = 0; i < interfacesActualTypes.length; i++) {
      ActualType tmpActualType = interfacesActualTypes[i];
      System.out.println(tmpActualType);

      if (i == 0) {
        Assertions.assertEquals(String.class, tmpActualType.getType(0));
      } else if (i == 1) {
        Assertions.assertEquals(String.class, tmpActualType.getType(0));
        Assertions.assertEquals(Integer.class, tmpActualType.getType(1));
      }
    }

    System.out.println("===>test method parameter type[0]");
    actualTypes = GenericTypeUtils.getMethodParamterActualType(testMethod, 1);
    System.out.println(actualTypes);
    Assertions.assertEquals(String.class, actualTypes.getType(0));
    Assertions.assertEquals(Integer.class, actualTypes.getType(1));

    System.out.println("===>test method parameter type[0][1]");
    actualTypeClass = GenericTypeUtils.getMethodParamterActualType(testMethod, 1, 1);
    System.out.println(actualTypeClass);
    Assertions.assertEquals(Integer.class, actualTypeClass);
  }
}

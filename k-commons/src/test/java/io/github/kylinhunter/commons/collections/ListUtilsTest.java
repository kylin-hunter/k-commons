package io.github.kylinhunter.commons.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ListUtilsTest {

  @Test
  void newArrayList() {

    List<Integer> list = ListUtils.newArrayList(1, 2);
    assertEquals(2, list.size());

    list = ListUtils.newArrayList();
    assertEquals(0, list.size());

    list = ListUtils.newArrayList(Arrays.asList(1, 3));
    assertEquals(2, list.size());

    list = ListUtils.newArrayListWithCapacity(3);
    assertEquals(0, list.size());
    List<String> list2 = ListUtils.newArrayList("1", "2", "3", "4");
    System.out.println(ListUtils.toString(list2));
    List<Integer> list3 = ListUtils.newArrayList(1, 2, 3, 4);
    System.out.println(ListUtils.toString(list3));
  }

  @Test
  void sort() {
    List<String> list = ListUtils.newArrayList("1", "3", "2", "5", "4");

    List<String> result = ListUtils.sort(list, s1 -> s1,
        ListUtils.newArrayList("1", "2", "3", "4", "5"));

    System.out.println(result);
    Assertions.assertEquals(result, ListUtils.newArrayList("1", "2", "3", "4", "5"));

    list = ListUtils.newArrayList("1", "3", "2", "5", "4");

    result = ListUtils.sort(list, s1 -> s1,
        ListUtils.newArrayList("1", "2", "3", "5", "4"));

    System.out.println(result);
    Assertions.assertEquals(result, ListUtils.newArrayList("1", "2", "3", "5", "4"));

    TestClass testClass1 = new TestClass("1");
    TestClass testClass2 = new TestClass("2");
    TestClass testClass3 = new TestClass("3");

    List<TestClass> list2 = ListUtils.newArrayList(testClass1, testClass3,
        testClass2);
    List<TestClass> result2 = ListUtils.sort(list2, s1 -> Integer.parseInt(s1.code),
        ListUtils.newArrayList(1, 2, 3));
    System.out.println(result2);
    Assertions.assertEquals(result2, ListUtils.newArrayList(testClass1, testClass2,
        testClass3));

    list2 = ListUtils.newArrayList(testClass1, testClass3,
        testClass2);
    result2 = ListUtils.sort(list2, s1 -> Integer.parseInt(s1.code),
        ListUtils.newArrayList(3, 2, 1));
    System.out.println(result2);
    Assertions.assertEquals(result2, ListUtils.newArrayList(testClass3, testClass2,
        testClass1));


  }

  @Data
  public static class TestClass {

    private final String code;

  }
}

package io.github.kylinhunter.commons.collections;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StreamUtilsTest {

  @Test
  void filter() {
    List<String> list = ListUtils.newArrayList();
    list.add("1");
    list.add("2");
    list.add("3");
    List<String> list1 = StreamUtils.andFilter(list.stream(), e -> e.equals("1"))
        .collect(Collectors.toList());
    System.out.println("=====");
    list1.forEach(System.out::println);
    Assertions.assertEquals(1, list1.size());

    List<String> list2 = StreamUtils
        .andFilter(list.stream(), e -> e.equals("1"), e -> e.equals("2"))
        .collect(Collectors.toList());
    System.out.println("=====");
    list2.forEach(System.out::println);

    Assertions.assertEquals(0, list2.size());

    List<String> list3 = StreamUtils.orFilter(list.stream(), e -> e.equals("1"))
        .collect(Collectors.toList());
    Assertions.assertEquals(1, list3.size());
    System.out.println("=====");
    list3.forEach(System.out::println);

    List<String> list4 = StreamUtils.orFilter(list.stream(), e -> e.equals("1"), e -> e.equals("2"))
        .collect(Collectors.toList());
    System.out.println("=====");
    list4.forEach(System.out::println);

    Assertions.assertEquals(2, list4.size());
  }
}
package io.github.kylinhunter.commons.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CollectionUtilsTest {

  @Test
  void merge() {
    List<Integer> l1 = ListUtils.newArrayList(1, 2);
    List<Integer> l2 = ListUtils.newArrayList(3, 4);
    List<Integer> mergeList = CollectionUtils.merge(true, l1, l2);

    Assertions.assertEquals(4, l1.size());
    Assertions.assertEquals(2, l2.size());
    Assertions.assertEquals(4, mergeList.size());

    l1 = ListUtils.newArrayList(1, 2);
    l2 = ListUtils.newArrayList(3, 4);
    mergeList = CollectionUtils.merge(false, l1, l2);

    Assertions.assertEquals(2, l1.size());
    Assertions.assertEquals(2, l2.size());
    Assertions.assertEquals(4, mergeList.size());
  }

  @SuppressWarnings("unchecked")
  @Test
  void filter() {
    List<String> list = ListUtils.newArrayList();
    list.add("1");
    list.add("2");
    list.add("3");
    List<String> list1 = CollectionUtils
        .andFilter(list, (Supplier<List<String>>) ArrayList::new, e -> e.equals("1"));
    System.out.println("=====");
    list1.forEach(System.out::println);
    Assertions.assertEquals(1, list1.size());

    List<String> list2 = CollectionUtils
        .andFilter(list, (Supplier<List<String>>) ArrayList::new, e -> e.equals("1"),
            e -> e.equals("2"));
    System.out.println("=====");
    list2.forEach(System.out::println);

    Assertions.assertEquals(0, list2.size());

    List<String> list3 = CollectionUtils
        .orFilter(list.stream(), (Supplier<List<String>>) ArrayList::new, e -> e.equals("1"));
    Assertions.assertEquals(1, list3.size());
    System.out.println("=====");
    list3.forEach(System.out::println);

    List<String> list4 = CollectionUtils
        .orFilter(list.stream(), (Supplier<List<String>>) ArrayList::new, e -> e.equals("1"),
            e -> e.equals("2"));
    System.out.println("=====");
    list4.forEach(System.out::println);

    Assertions.assertEquals(2, list4.size());
  }


}

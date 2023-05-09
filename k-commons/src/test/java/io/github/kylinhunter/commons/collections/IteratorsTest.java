package io.github.kylinhunter.commons.collections;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IteratorsTest {

  @Test
  void test() {
    List list1 = ListUtils.newArrayList(1, 2, 3);
    List list2 = ListUtils.newArrayList(1, 2, 3);

    Iterators.addAll(list1, list2.iterator());
    Assertions.assertEquals(6, list1.size());
  }

}
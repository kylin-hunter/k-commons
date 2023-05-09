package io.github.kylinhunter.commons.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
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

  }
}
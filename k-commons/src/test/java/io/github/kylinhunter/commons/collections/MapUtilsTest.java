package io.github.kylinhunter.commons.collections;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MapUtilsTest {

  @Test
  void test() {
    Map<Object, Object> map1 = MapUtils.newHashMap();
    Assertions.assertTrue(map1 instanceof HashMap);

    Map<Object, Object> map11 = MapUtils.newHashMap(1);
    Assertions.assertTrue(map11 instanceof HashMap);

    Map<Object, Object> map12 = MapUtils.newHashMap(Integer.MAX_VALUE);
    Assertions.assertTrue(map12 instanceof HashMap);

    Map<Comparable, Object> map2 = MapUtils.newTreeMap();
    Assertions.assertTrue(map2 instanceof TreeMap);

    Map<Object, Object> map3 = MapUtils.newLinkedHashMap();
    Assertions.assertTrue(map3 instanceof LinkedHashMap);

    ConcurrentMap<Object, Object> map4 = MapUtils.newConcurrentMap();
    Assertions.assertTrue(map4 instanceof ConcurrentMap);

    TreeMap<String, Object> map5 =
        MapUtils.newTreeMap(Comparator.comparingInt(String::length).reversed());
    Assertions.assertTrue(map5 instanceof TreeMap);
  }
}

package io.github.kylinhunter.commons.reflect.test.generic;

import java.util.List;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 19:02
 */
public class TestClassForGeneric extends S<Integer, String>
    implements I1<String, Integer>, I2<Integer, String> {

  List<String> list;
  Map<String, Integer> map;

  public Map<String, Integer> test(List<String> list, Map<String, Integer> map) {
    return null;
  }
}

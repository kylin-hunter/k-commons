package io.github.kylinhunter.commons.collections;

import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MultiValueMapTest {

  @Test
  void test1() {
    String key = "a";
    MultiValueMap<String, String> map = new MultiValueMap<>(false);
    map.add(key, "a1");
    Assertions.assertNull(map.getValues(key + "-"));

    Assertions.assertEquals(1, map.getValues(key).size());
    map.add(key, "a2");
    map.add(key, "a2");
    Assertions.assertEquals(3, map.getValues(key).size());

    Assertions.assertEquals("a1", map.getValue(key));

    boolean removeResult = map.remove(key, "a2");
    Assertions.assertTrue(removeResult);

    Assertions.assertEquals(2, map.getValues(key).size());

    removeResult = map.remove(key, "a2");
    Assertions.assertTrue(removeResult);

    Assertions.assertEquals(1, map.getValues(key).size());

    removeResult = map.remove(key, "a2");
    Assertions.assertFalse(removeResult);

    Assertions.assertEquals(1, map.getValues(key).size());

    Collection<String> remove = map.remove(key);

    Assertions.assertEquals(1, remove.size());

    remove = map.remove(key);

    Assertions.assertNull(remove);
  }

  @Test
  void test2() {
    String key = "a";
    MultiValueMap<String, String> map = new MultiValueMap<>();
    map.add(key, "a1");
    Assertions.assertNull(map.getValues(key + "-"));

    Assertions.assertEquals(1, map.getValues(key).size());
    map.add(key, "a2");
    map.add(key, "a2");
    Assertions.assertEquals(2, map.getValues(key).size());

    Assertions.assertTrue(!StringUtil.isEmpty(map.getValue(key)));

    boolean removeResult = map.remove(key, "a2");
    Assertions.assertTrue(removeResult);

    Assertions.assertEquals(1, map.getValues(key).size());

    removeResult = map.remove(key, "a2");
    Assertions.assertFalse(removeResult);

    Assertions.assertEquals(1, map.getValues(key).size());

    Collection<String> remove = map.remove(key);

    Assertions.assertEquals(1, remove.size());

    remove = map.remove(key);

    Assertions.assertNull(remove);
  }
}

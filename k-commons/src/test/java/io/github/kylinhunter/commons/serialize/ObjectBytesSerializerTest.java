package io.github.kylinhunter.commons.serialize;

import io.github.kylinhunter.commons.collections.ListUtils;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectBytesSerializerTest {

  @Test
  void test1() {
    ArrayList<String> list1 = ListUtils.newArrayList("1", "2", "3");
    byte[] serialize = ObjectBytesSerializer.serialize(list1);
    ArrayList<String> list2 = ObjectBytesSerializer.deserialize(serialize);
    Assertions.assertEquals(list1, list2);

  }

  @Test
  void test2() {
    ArrayList<String> list1 = ListUtils.newArrayList("1", "2", "3");
    String text = ObjectBytesSerializer.serializeToString(list1);
    System.out.println(text);
    ArrayList<String> list2 = ObjectBytesSerializer.deserialize(text);
    Assertions.assertEquals(list1, list2);
  }
}

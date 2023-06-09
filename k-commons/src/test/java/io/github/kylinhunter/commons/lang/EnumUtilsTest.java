package io.github.kylinhunter.commons.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnumUtilsTest {

  @Test
  void testFromLabel() {
    TestEnumLabel testEnumTag = EnumUtils.fromLabel(TestEnumLabel.class, "1");
    assertEquals(TestEnumLabel.TEST1, testEnumTag);
    testEnumTag = EnumUtils.fromLabel(TestEnumLabel.class, "2");
    assertEquals(TestEnumLabel.TEST2, testEnumTag);

    TestEnumLabel[] testEnumTags =
        EnumUtils.fromLabel(TestEnumLabel.class, new String[] {"1", "2"});

    assertEquals(TestEnumLabel.TEST1, testEnumTags[0]);
    assertEquals(TestEnumLabel.TEST2, testEnumTags[1]);
  }

  @Test
  void testFromCode() {
    TestEnumCode testEnumCode = EnumUtils.fromCode(TestEnumCode.class, 1);
    assertEquals(TestEnumCode.TEST1, testEnumCode);
    testEnumCode = EnumUtils.fromCode(TestEnumCode.class, 2);
    assertEquals(TestEnumCode.TEST2, testEnumCode);

    TestEnumCode[] testEnumCodes = EnumUtils.fromCode(TestEnumCode.class, new int[] {1, 2});

    assertEquals(TestEnumCode.TEST1, testEnumCodes[0]);
    assertEquals(TestEnumCode.TEST2, testEnumCodes[1]);
  }

  @Test
  void testFromName() {
    TestEnumCode testEnumCode = EnumUtils.fromName(TestEnumCode.class, "TEST1");
    assertEquals(TestEnumCode.TEST1, testEnumCode);
    testEnumCode = EnumUtils.fromName(TestEnumCode.class, "TEST2");
    assertEquals(TestEnumCode.TEST2, testEnumCode);

    TestEnumCode[] testEnumCodes =
        EnumUtils.fromName(TestEnumCode.class, new String[] {"TEST1", "TEST2"});

    assertEquals(TestEnumCode.TEST1, testEnumCodes[0]);
    assertEquals(TestEnumCode.TEST2, testEnumCodes[1]);
  }

  @Test
  void testThrows() {

    Assertions.assertThrows(
        KRuntimeException.class, () -> EnumUtils.fromCode(TestEnumCode.class, 7, true));

    assertNull(EnumUtils.fromCode(TestEnumCode.class, 7, false));
    Assertions.assertThrows(
        KRuntimeException.class,
        () -> EnumUtils.fromCode(TestEnumCode.class, new int[] {1, 7}, true));
    assertNull(EnumUtils.fromCode(TestEnumCode.class, new int[] {1, 7}, false));

    Assertions.assertThrows(
        KRuntimeException.class, () -> EnumUtils.fromName(TestEnumCode.class, "test", true));

    assertNull(EnumUtils.fromName(TestEnumCode.class, "test", false));
    Assertions.assertThrows(
        KRuntimeException.class,
        () -> EnumUtils.fromName(TestEnumCode.class, new String[] {"TEST1", "TEST21"}, true));
    assertNull(EnumUtils.fromName(TestEnumCode.class, new String[] {"TEST1", "TEST21"}, false));
  }

  @RequiredArgsConstructor
  public enum TestEnumCode implements EnumUtils.EnumCode {
    TEST1(1),

    TEST2(2);

    @Getter private final int code;
  }

  @RequiredArgsConstructor
  public enum TestEnumLabel implements EnumUtils.EnumLabel {
    TEST1("1"),

    TEST2("2");

    @Getter private final String label;
  }
}

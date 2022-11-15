package com.kylinhunter.plat.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

class EnumUtilTest {

    @Test
    void testFromLabel() {
        TestEnumLabel testEnumTag = EnumUtil.fromLabel(TestEnumLabel.class, "1");
        assertEquals(TestEnumLabel.TEST1, testEnumTag);
        testEnumTag = EnumUtil.fromLabel(TestEnumLabel.class, "2");
        assertEquals(TestEnumLabel.TEST2, testEnumTag);

        TestEnumLabel[] testEnumTags = EnumUtil.fromLabel(TestEnumLabel.class, new String[] {"1", "2"});

        assertEquals(TestEnumLabel.TEST1, testEnumTags[0]);
        assertEquals(TestEnumLabel.TEST2, testEnumTags[1]);

    }

    @Test
    void testFromCode() {
        TestEnumCode testEnumCode = EnumUtil.fromCode(TestEnumCode.class, 1);
        assertEquals(TestEnumCode.TEST1, testEnumCode);
        testEnumCode = EnumUtil.fromCode(TestEnumCode.class, 2);
        assertEquals(TestEnumCode.TEST2, testEnumCode);

        TestEnumCode[] testEnumCodes = EnumUtil.fromCode(TestEnumCode.class, new int[] {1, 2});

        assertEquals(TestEnumCode.TEST1, testEnumCodes[0]);
        assertEquals(TestEnumCode.TEST2, testEnumCodes[1]);

    }

    @Test
    void testFromName() {
        TestEnumCode testEnumCode = EnumUtil.fromName(TestEnumCode.class, "TEST1");
        assertEquals(TestEnumCode.TEST1, testEnumCode);
        testEnumCode = EnumUtil.fromName(TestEnumCode.class, "TEST2");
        assertEquals(TestEnumCode.TEST2, testEnumCode);

        TestEnumCode[] testEnumCodes = EnumUtil.fromName(TestEnumCode.class, new String[] {"TEST1", "TEST2"});

        assertEquals(TestEnumCode.TEST1, testEnumCodes[0]);
        assertEquals(TestEnumCode.TEST2, testEnumCodes[1]);

    }

    @Test
    void testThrows() {

        Assertions.assertThrows(KRuntimeException.class, () -> EnumUtil.fromCode(TestEnumCode.class, 7, true));

        assertNull(EnumUtil.fromCode(TestEnumCode.class, 7, false));
        Assertions
                .assertThrows(KRuntimeException.class,
                        () -> EnumUtil.fromCode(TestEnumCode.class, new int[] {1, 7}, true));
        assertNull(EnumUtil.fromCode(TestEnumCode.class, new int[] {1, 7}, false));

        Assertions.assertThrows(KRuntimeException.class, () -> EnumUtil.fromName(TestEnumCode.class, "test", true));

        assertNull(EnumUtil.fromName(TestEnumCode.class, "test", false));
        Assertions.assertThrows(KRuntimeException.class,
                () -> EnumUtil.fromName(TestEnumCode.class, new String[] {"TEST1", "TEST21"}, true));
        assertNull(EnumUtil.fromName(TestEnumCode.class, new String[] {"TEST1", "TEST21"}, false));

    }

    public enum TestEnumCode implements EnumUtil.EnumCode {
        TEST1(1),

        TEST2(2);

        @Getter
        private final int code;

        TestEnumCode(int code) {
            this.code = code;
        }

    }

    @RequiredArgsConstructor
    public enum TestEnumLabel implements EnumUtil.EnumLabel {
        TEST1("1"),

        TEST2("2");

        @Getter
        private final String label;

    }
}
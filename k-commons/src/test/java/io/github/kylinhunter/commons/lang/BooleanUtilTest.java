package io.github.kylinhunter.commons.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BooleanUtilTest {

    @Test
    void test() {
        Assertions.assertTrue(BooleanUtil.toBoolean("1"));
        Assertions.assertTrue(BooleanUtil.toBoolean("true"));
        Assertions.assertTrue(BooleanUtil.toBoolean("yes"));

        Assertions.assertFalse(BooleanUtil.toBoolean("0"));
        Assertions.assertFalse(BooleanUtil.toBoolean("false"));
        Assertions.assertFalse(BooleanUtil.toBoolean("no"));

        Assertions.assertFalse(BooleanUtil.toBoolean("adfs"));
    }
}

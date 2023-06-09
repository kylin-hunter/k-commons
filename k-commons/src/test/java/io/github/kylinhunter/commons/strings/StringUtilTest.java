package io.github.kylinhunter.commons.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.lang.strings.StringUtil;

class StringUtilTest {

    @Test
    void test() {

        assertTrue(StringUtil.isEmpty(""));
        assertTrue(StringUtil.isBlank(" "));
        assertEquals("a", StringUtil.defaultIfBlank(" ", "a"));

        assertEquals("a", StringUtil.defaultIfBlank("", "a"));
        assertEquals(3, StringUtil.length("123"));

        assertEquals("a", StringUtil.defaultString(null, "a"));
        assertEquals("", StringUtil.defaultString(null));
    }
}

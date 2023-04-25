package io.github.kylinhunter.commons.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IOCaseTest {

    @Test
    void test() {
        IOCase ioCase = IOCase.SENSITIVE;
        Assertions.assertTrue(ioCase.checkCompareTo("ABC", "abc") != 0);
        Assertions.assertFalse(ioCase.checkEquals("ABC", "abc"));
        Assertions.assertFalse(ioCase.checkEndsWith("ABC", "bc"));
        Assertions.assertFalse(ioCase.checkStartsWith("ABC", "ab"));
        Assertions.assertFalse(ioCase.checkIndexOf("ABCDE", 1, "cd") > 0);
        Assertions.assertFalse(ioCase.checkRegionMatches("ABCDE", 2, "cd"));

        ioCase = IOCase.INSENSITIVE;
        Assertions.assertTrue(ioCase.checkCompareTo("ABC", "abc") == 0);
        Assertions.assertTrue(ioCase.checkEquals("ABC", "abc"));
        Assertions.assertTrue(ioCase.checkEndsWith("ABC", "bc"));
        Assertions.assertTrue(ioCase.checkStartsWith("ABC", "ab"));
        Assertions.assertTrue(ioCase.checkIndexOf("ABCDE", 1, "cd") > 0);
        Assertions.assertTrue(ioCase.checkRegionMatches("ABCDE", 2, "cd"));

    }

}
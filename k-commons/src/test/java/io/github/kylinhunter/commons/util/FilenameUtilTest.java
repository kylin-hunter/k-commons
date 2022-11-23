package io.github.kylinhunter.commons.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FilenameUtilTest {

    @Test
    void getExtension() {
        String fileName="a.txt";
        assertEquals("txt", FilenameUtils.getExtension(fileName));

        fileName="a.tar.gz";
        assertEquals("tar.gz", FilenameUtils.getExtension(fileName));

        fileName="a.tar1.gz";
        assertEquals("gz", FilenameUtils.getExtension(fileName));

        fileName="a.tar.z";
        assertEquals("tar.z", FilenameUtils.getExtension(fileName));

        fileName="a.tar1.z";
        assertEquals("z", FilenameUtils.getExtension(fileName));
    }
}
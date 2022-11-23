package io.github.kylinhunter.commons.io.file.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.io.ResourceHelper;

class FileReaderUtilsTest {

    @Test
    void process() {
        String path = "classpath:/test/file/test1.txt";
        File distFile = ResourceHelper.getFile(path);
        AtomicInteger lines = new AtomicInteger(0);
        FileReaderUtils.process(distFile, "UTF-8", (line -> lines.incrementAndGet()));
        assertEquals(1, lines.get());

        FileStatLineProcessor fileStatLinesProcessor = new FileStatLineProcessor();
        FileReaderUtils.process(distFile, "UTF-8", fileStatLinesProcessor);
        assertEquals(1, fileStatLinesProcessor.getResult().getLineNum());

    }

}
package io.github.kylinhunter.commons.io.file.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.kylinhunter.commons.io.ResourceHelper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderUtilsTest {

  @Test
  void process() throws IOException {
    String path = "classpath:/test/file/test1.txt";
    File distFile = ResourceHelper.getFile(path);
    AtomicInteger lines = new AtomicInteger(0);
    FileReaderUtils.process(distFile, "UTF-8", (line -> lines.incrementAndGet()));
    assertEquals(3, lines.get());

    FileStatLineProcessor fileStatLinesProcessor = new FileStatLineProcessor();
    FileReaderUtils.process(distFile, "UTF-8", fileStatLinesProcessor);
    assertEquals(3, fileStatLinesProcessor.getResult().getLineNum());

    byte[] bytes1 = FileUtils.readFileToByteArray(distFile);
    byte[] bytes2 = FileReaderUtils.readFileToByteArray(distFile);

    Assertions.assertTrue(bytes2.length > 0);
    Assertions.assertArrayEquals(bytes1, bytes2);
  }
}

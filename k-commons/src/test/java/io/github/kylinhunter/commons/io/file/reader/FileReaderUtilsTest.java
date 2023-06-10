package io.github.kylinhunter.commons.io.file.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.kylinhunter.commons.io.ResourceHelper;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderUtilsTest {

  @Test
  void process() {
    String path = "classpath:/test/file/test1.txt";
    File distFile = ResourceHelper.getFile(path);
    AtomicInteger lines = new AtomicInteger(0);
    FileReaderUtils.process(distFile, "UTF-8", (line -> lines.incrementAndGet()));
    assertEquals(2, lines.get());

    FileStatLineProcessor fileStatLinesProcessor = new FileStatLineProcessor();
    FileReaderUtils.process(distFile, "UTF-8", fileStatLinesProcessor);
    assertEquals(2, fileStatLinesProcessor.getResult().getLineNum());

    byte[] bytes = FileReaderUtils.readFileToByteArray(distFile);
    Assertions.assertTrue(bytes.length > 0);
  }
}

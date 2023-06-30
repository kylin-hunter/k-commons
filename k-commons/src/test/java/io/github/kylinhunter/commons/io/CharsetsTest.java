package io.github.kylinhunter.commons.io;

import java.nio.charset.Charset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CharsetsTest {

  @Test
  void toCharset() {
    Charset charset = Charsets.toCharset(Charsets.UTF_8_VALUE);
    Assertions.assertTrue(charset == Charsets.UTF_8);
  }
}

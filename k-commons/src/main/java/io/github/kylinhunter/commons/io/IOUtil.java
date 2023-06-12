package io.github.kylinhunter.commons.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-11 10:46
 */
public class IOUtil {

  public static List<String> readLines(final InputStream input, final Charset charset)
      throws IOException {
    final InputStreamReader reader = new InputStreamReader(input, Charsets.toCharset(charset));
    return readLines(reader);
  }

  @SuppressWarnings("resource") // reader wraps input and is the responsibility of the caller.
  public static List<String> readLines(final Reader reader) throws IOException {
    final BufferedReader bufReader = toBufferedReader(reader);
    final List<String> list = new ArrayList<>();
    String line;
    while ((line = bufReader.readLine()) != null) {
      list.add(line);
    }
    return list;
  }

  /**
   * @param reader reader
   * @return java.io.BufferedReader
   * @title toBufferedReader
   * @description toBufferedReader
   * @author BiJi'an
   * @date 2023-06-11 10:49
   */
  public static BufferedReader toBufferedReader(final Reader reader) {
    return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
  }

}
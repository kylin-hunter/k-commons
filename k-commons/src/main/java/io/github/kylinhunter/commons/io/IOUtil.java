package io.github.kylinhunter.commons.io;

import io.github.kylinhunter.commons.io.output.StringBuilderWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-11 10:46
 */
public class IOUtil {

  public static final int EOF = -1;
  public static final int DEFAULT_BUFFER_SIZE = 8192;

  private static final ThreadLocal<char[]> SKIP_CHAR_BUFFER = ThreadLocal.withInitial(
      IOUtil::charArray);

  private static char[] charArray() {
    return charArray(DEFAULT_BUFFER_SIZE);
  }

  private static char[] charArray(final int size) {
    return new char[size];
  }

  /**
   * @param input   input
   * @param charset charset
   * @return java.util.List<java.lang.String>
   * @throws
   * @title readLines
   * @description readLines
   * @author BiJi'an
   * @date 2023-06-12 22:17
   */
  public static List<String> readLines(final InputStream input, final Charset charset)
      throws IOException {
    final InputStreamReader reader = new InputStreamReader(input, Charsets.toCharset(charset));
    return readLines(reader);
  }

  /**
   * @param reader reader
   * @return java.util.List<java.lang.String>
   * @throws
   * @title readLines
   * @description readLines
   * @author BiJi'an
   * @date 2023-06-12 22:17
   */
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

  /**
   * @param input   input
   * @param charset charset
   * @return java.lang.String
   * @throws
   * @title toString
   * @description toString
   * @author BiJi'an
   * @date 2023-06-12 22:17
   */
  public static String toString(final InputStream input, final Charset charset) throws IOException {
    try (final StringBuilderWriter sw = new StringBuilderWriter()) {
      copy(input, sw, charset);
      return sw.toString();
    }
  }

  /**
   * @param input        input
   * @param writer       writer
   * @param inputCharset inputCharset
   * @return void
   * @throws
   * @title copy
   * @description copy
   * @author BiJi'an
   * @date 2023-06-12 22:59
   */
  public static void copy(final InputStream input, final Writer writer, final Charset inputCharset)
      throws IOException {
    final InputStreamReader reader = new InputStreamReader(input, Charsets.toCharset(inputCharset));
    copy(reader, writer);
  }

  /**
   * @param reader reader
   * @param writer writer
   * @return int
   * @throws
   * @title copy
   * @description copy
   * @author BiJi'an
   * @date 2023-06-12 22:59
   */
  public static int copy(final Reader reader, final Writer writer) throws IOException {
    final long count = copyLarge(reader, writer);
    if (count > Integer.MAX_VALUE) {
      return EOF;
    }
    return (int) count;
  }

  /**
   * @param reader reader
   * @param writer writer
   * @return long
   * @throws
   * @title copyLarge
   * @description copyLarge
   * @author BiJi'an
   * @date 2023-06-12 22:59
   */
  public static long copyLarge(final Reader reader, final Writer writer) throws IOException {
    return copyLarge(reader, writer, getCharArray());
  }

  /**
   * @param reader reader
   * @param writer writer
   * @param buffer buffer
   * @return long
   * @throws
   * @title copyLarge
   * @description copyLarge
   * @author BiJi'an
   * @date 2023-06-12 22:59
   */
  public static long copyLarge(final Reader reader, final Writer writer, final char[] buffer)
      throws IOException {
    long count = 0;
    int n;
    while (EOF != (n = reader.read(buffer))) {
      writer.write(buffer, 0, n);
      count += n;
    }
    return count;
  }

  /**
   * @return char[]
   * @throws
   * @title getCharArray
   * @description getCharArray
   * @author BiJi'an
   * @date 2023-06-12 22:59
   */
  static char[] getCharArray() {
    return SKIP_CHAR_BUFFER.get();
  }

}
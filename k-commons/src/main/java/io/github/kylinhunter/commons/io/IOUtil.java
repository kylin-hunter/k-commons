/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.io;

import io.github.kylinhunter.commons.collections.ArrayUtils;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.output.StringBuilderWriter;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-11 10:46
 */
public class IOUtil {

  public static final int EOF = -1;
  public static final int DEFAULT_BUFFER_SIZE = 8192;
  public static final byte[] EMPTY_BYTE_ARRAY = {};

  private static final ThreadLocal<char[]> SKIP_CHAR_BUFFER =
      ThreadLocal.withInitial(IOUtil::charArray);

  private static char[] charArray() {
    return charArray(DEFAULT_BUFFER_SIZE);
  }

  /**
   * @param size size
   * @return char[]
   * @title charArray
   * @description charArray
   * @author BiJi'an
   * @date 2023-06-12 23:36
   */
  private static char[] charArray(final int size) {
    return new char[size];
  }

  /**
   * @param size size
   * @return byte[]
   * @title byteArray
   * @description byteArray
   * @author BiJi'an
   * @date 2023-06-12 23:54
   */
  public static byte[] byteArray(final int size) {
    return new byte[size];
  }

  /**
   * @return byte[]
   * @title byteArray
   * @description byteArray
   * @author BiJi'an
   * @date 2023-06-12 23:54
   */
  public static byte[] byteArray() {
    return byteArray(DEFAULT_BUFFER_SIZE);
  }

  /**
   * @param input input
   * @param charset charset
   * @return java.util.List<java.lang.String>
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
   * @title readLines
   * @description readLines
   * @author BiJi'an
   * @date 2023-06-12 22:17
   */
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
   * @param input input
   * @param charset charset
   * @return java.lang.String
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
   * @param input input
   * @param writer writer
   * @param inputCharset inputCharset
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
   * @title getCharArray
   * @description getCharArray
   * @author BiJi'an
   * @date 2023-06-12 22:59
   */
  static char[] getCharArray() {
    return SKIP_CHAR_BUFFER.get();
  }

  /**
   * @param input input
   * @param size size
   * @return byte[]
   * @title toByteArray
   * @description toByteArray
   * @author BiJi'an
   * @date 2023-06-12 23:35
   */
  public static byte[] toByteArray(final InputStream input, final long size) throws IOException {

    if (size > Integer.MAX_VALUE) {
      throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
    }

    return toByteArray(input, (int) size);
  }

  /**
   * @param input input
   * @param size size
   * @return byte[]
   * @title toByteArray
   * @description toByteArray
   * @author BiJi'an
   * @date 2023-06-12 23:35
   */
  public static byte[] toByteArray(final InputStream input, final int size) throws IOException {

    if (size < 0) {
      throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
    }

    if (size == 0) {
      return EMPTY_BYTE_ARRAY;
    }

    final byte[] data = IOUtil.byteArray(size);
    int offset = 0;
    int read;

    while (offset < size && (read = input.read(data, offset, size - offset)) != EOF) {
      offset += read;
    }

    if (offset != size) {
      throw new IOException("Unexpected read size, current: " + offset + ", expected: " + size);
    }

    return data;
  }

  /**
   * @param inputStream inputStream
   * @return byte[]
   * @title toByteArray
   * @description toByteArray
   * @author BiJi'an
   * @date 2023-06-12 23:35
   */
  public static byte[] toByteArray(final InputStream inputStream) {
    try (final ByteArrayOutputStream ubaOutput = new ByteArrayOutputStream()) {
      copy(inputStream, ubaOutput);
      return ubaOutput.toByteArray();
    } catch (IOException e) {
      throw new KIOException("toByteArray error", e);
    }
  }

  /**
   * @param inputStream inputStream
   * @param outputStream outputStream
   * @return int
   * @title copy
   * @description copy
   * @author BiJi'an
   * @date 2023-06-12 23:35
   */
  public static int copy(final InputStream inputStream, final OutputStream outputStream) {
    final long count = copyLarge(inputStream, outputStream);
    if (count > Integer.MAX_VALUE) {
      return EOF;
    }
    return (int) count;
  }

  /**
   * @param inputStream inputStream
   * @param outputStream outputStream
   * @return long
   * @title copyLarge
   * @description copyLarge
   * @author BiJi'an
   * @date 2023-06-12 23:35
   */
  public static long copyLarge(final InputStream inputStream, final OutputStream outputStream) {
    return copy(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
  }

  /**
   * @param inputStream inputStream
   * @param outputStream outputStream
   * @param bufferSize bufferSize
   * @return long
   * @title copy
   * @description copy
   * @author BiJi'an
   * @date 2023-06-12 23:35
   */
  public static long copy(
      final InputStream inputStream, final OutputStream outputStream, final int bufferSize) {
    return copyLarge(inputStream, outputStream, IOUtil.byteArray(bufferSize));
  }

  /**
   * @param inputStream inputStream
   * @param outputStream outputStream
   * @param buffer buffer
   * @return long
   * @title copyLarge
   * @description copyLarge
   * @author BiJi'an
   * @date 2023-06-12 23:35
   */
  public static long copyLarge(
      final InputStream inputStream, final OutputStream outputStream, final byte[] buffer) {
    Objects.requireNonNull(inputStream, "inputStream");
    Objects.requireNonNull(outputStream, "outputStream");
    long count = 0;
    int n;
    try {
      while (EOF != (n = inputStream.read(buffer))) {
        outputStream.write(buffer, 0, n);
        count += n;
      }
    } catch (IOException e) {
      throw new KIOException("copyLarge error", e);
    }
    return count;
  }

  /**
   * @param c c
   * @title closeQuietly
   * @description closeQuietly
   * @author BiJi'an
   * @date 2023-06-21 02:09
   */
  public static void closeQuietly(final Closeable c) {
    if (c != null) {
      try {
        c.close();
      } catch (final IOException ignored) { // NOPMD NOSONAR
      }
    }
  }

  /**
   * @param c c
   * @title closeQuietly
   * @description closeQuietly
   * @author BiJi'an
   * @date 2023-12-07 00:15
   */
  public static void closeQuietly(final AutoCloseable c) {
    if (c != null) {
      try {
        c.close();
      } catch (final Exception ignored) { // NOPMD NOSONAR
      }
    }
  }

  /**
   * @param body body
   * @param charset charset
   * @param charLen charLen
   * @return java.lang.String
   * @title toString
   * @description toString
   * @author BiJi'an
   * @date 2023-09-26 19:39
   */
  public static String toString(byte[] body, Charset charset, int charLen) {
    if (ArrayUtils.isEmpty(body)) {
      return StringUtil.EMPTY;
    }
    charset = Charsets.defaultCharset(charset, Charsets.UTF_8);
    if (charLen <= 0 || body.length < charLen) {
      return new String(body, charset);
    }
    try (Reader reader = new InputStreamReader(new ByteArrayInputStream(body), charset)) {
      return toString(reader, charLen);
    } catch (IOException e) {
      throw new KIOException("toString error", e);
    }
  }

  public static String toString(InputStream input, Charset charset, int charLen) {
    if (input == null) {
      return StringUtil.EMPTY;
    }
    charset = Charsets.defaultCharset(charset, Charsets.UTF_8);
    if (charLen <= 0) {

      byte[] bytes = IOUtil.toByteArray(input);
      return new String(bytes, charset);
    }
    try (Reader reader = new InputStreamReader(input, charset)) {
      return toString(reader, charLen);
    } catch (IOException e) {
      throw new KIOException("toString error", e);
    }
  }

  /**
   * @param reader reader
   * @param charLen charLen
   * @return java.lang.String
   * @throws IOException IOException
   * @title toString
   * @description toString
   * @author BiJi'an
   * @date 2023-09-26 23:59
   */
  public static String toString(Reader reader, int charLen) throws IOException {
    CharBuffer result = CharBuffer.allocate(charLen);
    int readLength = reader.read(result);
    result.flip();
    if (readLength > 0) {
      String resultText = result.toString();
      result.clear();
      readLength = reader.read(result);
      if (readLength > 0) {
        resultText += "...";
      }
      return resultText;
    }
    return "";
  }
}

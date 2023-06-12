package io.github.kylinhunter.commons.io.file.reader;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.Charsets;
import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.io.file.FileUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class FileReaderUtils {

  /**
   * @param file      a file
   * @param encoding  the encoding of the file
   * @param processor a processor to process the file
   * @title process
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:48
   */
  public static void process(File file, String encoding, LineProcessor processor) {

    try (InputStream input = Files.newInputStream(file.toPath())) {
      process(input, encoding, processor);
    } catch (Exception e) {
      throw new KIOException("process error", e);
    }
  }

  /**
   * @param input     the input stream
   * @param encoding  the encoding
   * @param processor the processor
   * @title process
   * @description
   * @author BiJi'an
   * @date 2022-01-01 02:00
   */
  public static void process(InputStream input, String encoding, LineProcessor processor) {

    try (InputStreamReader streamReader =
        new InputStreamReader(input, Charsets.toCharset(encoding));
        BufferedReader bufferReader = new BufferedReader(streamReader)) {
      String line = bufferReader.readLine();
      while (line != null) {
        processor.process(line);
        line = bufferReader.readLine();
      }
    } catch (Exception e) {
      throw new KIOException("process error", e);
    }
  }

  /**
   * @param file      a file
   * @param encoding  the encoding of the file
   * @param processor a processor to process the file
   * @title process
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:48
   */
  @SuppressWarnings("UnusedReturnValue")
  public static <T> T process(File file, String encoding, DefaultLineProcessor<T> processor) {

    try (InputStream input = Files.newInputStream(file.toPath())) {
      process(input, encoding, processor);
      return processor.getResult();
    } catch (Exception e) {
      throw new KIOException("process error", e);
    }
  }

  /**
   * @param file file
   * @return byte[]
   * @title readFileToByteArray
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:32
   */
  public static byte[] readFileToByteArray(final File file) {
    try (InputStream inputStream = FileUtil.openInputStream(file)) {
      final long fileLength = file.length();
      // file.length() may return 0 for system-dependent entities, treat 0 as unknown length - see
      // IO-453
      return fileLength > 0
          ? IOUtil.toByteArray(inputStream, fileLength)
          : IOUtil.toByteArray(inputStream);
    } catch (IOException e) {
      throw new KIOException("readFileToByteArray error", e);
    }
  }


}

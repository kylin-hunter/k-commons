package io.github.kylinhunter.commons.cmd;

import io.github.kylinhunter.commons.io.Charsets;
import io.github.kylinhunter.commons.io.IOUtil;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 10:35
 */
@RequiredArgsConstructor
@AllArgsConstructor
public class CmdResultReader implements Callable<List<String>> {

  private final Process process;
  private final ResultType type;
  private String charset;

  @Override
  public List<String> call() throws Exception {

    if (type == ResultType.STD_OUT) {
      try (InputStream stream = process.getInputStream()) {

        return IOUtil.readLines(stream, Charsets.toCharset(charset));
      }
    } else {
      try (InputStream stream = process.getErrorStream()) {
        return IOUtil.readLines(stream, Charsets.toCharset(charset));
      }
    }
  }

  enum ResultType {
    STD_OUT, // the  standard ouput stream
    STD_ERR // the standard err stream
  }
}

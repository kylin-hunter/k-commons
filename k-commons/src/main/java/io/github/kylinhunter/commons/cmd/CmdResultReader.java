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

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
package io.github.kylinhunter.commons.io.file.filter;

import io.github.kylinhunter.commons.io.IOCase;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 19:12
 */
public class SuffixPathFilter extends AbstractPathFilter {
  private final String[] suffixes;
  private final IOCase ioCase;

  public SuffixPathFilter(final String... suffixes) {
    this(suffixes, IOCase.SENSITIVE);
  }

  public SuffixPathFilter(final String[] suffixes, final IOCase ioCase) {
    Objects.requireNonNull(suffixes, "suffixes must not be null");
    this.suffixes = new String[suffixes.length];
    System.arraycopy(suffixes, 0, this.suffixes, 0, suffixes.length);
    initSuffixes();
    this.ioCase = ioCase == null ? IOCase.SENSITIVE : ioCase;
  }

  public SuffixPathFilter(final List<String> suffixes) {
    this(suffixes, IOCase.SENSITIVE);
  }

  public SuffixPathFilter(final List<String> suffixes, final IOCase ioCase) {
    Objects.requireNonNull(suffixes, "suffixes must not be null");
    this.suffixes = suffixes.toArray(StringUtil.EMPTY_STRING_ARRAY);
    initSuffixes();
    this.ioCase = ioCase == null ? IOCase.SENSITIVE : ioCase;
  }

  /**
   * @return void
   * @title initSuffixes
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:49
   */
  private void initSuffixes() {
    for (int i = 0; i < suffixes.length; i++) {
      if (!this.suffixes[i].startsWith(".")) {
        this.suffixes[i] = "." + suffixes[i];
      }
    }
  }

  @Override
  public FileVisitResult accept(final Path file, final BasicFileAttributes attributes) {
    if (accept(Objects.toString(file.getFileName(), null))) {
      return FileVisitResult.CONTINUE;
    } else {
      return FileVisitResult.TERMINATE;
    }
  }

  /**
   * @param name name
   * @return boolean
   * @title accept
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:49
   */
  private boolean accept(final String name) {
    for (final String suffix : this.suffixes) {
      if (ioCase.checkEndsWith(name, suffix)) {
        return true;
      }
    }
    return false;
  }
}

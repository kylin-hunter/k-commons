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

import io.github.kylinhunter.commons.collections.ListUtils;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;

public class OrPathFilter extends AbstractPathFilter {
  private final List<PathFilter> fileFilters;

  public OrPathFilter(List<PathFilter> fileFilters) {
    this.fileFilters = Objects.requireNonNull(fileFilters, "fileFilters");
  }

  public OrPathFilter(final PathFilter filter1, final PathFilter filter2) {
    this.fileFilters = ListUtils.newArrayListWithCapacity(2);
    addPathFilter(filter1);
    addPathFilter(filter2);
  }

  public void addPathFilter(final PathFilter... fileFilters) {
    for (final PathFilter fileFilter : Objects.requireNonNull(fileFilters, "fileFilters")) {
      this.fileFilters.add(fileFilter);
    }
  }

  @Override
  public FileVisitResult accept(final Path file, final BasicFileAttributes attributes) {
    for (final PathFilter fileFilter : fileFilters) {
      if (fileFilter.accept(file, attributes) == FileVisitResult.CONTINUE) {
        return FileVisitResult.CONTINUE;
      }
    }
    return FileVisitResult.TERMINATE;
  }
}

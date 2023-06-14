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
package io.github.kylinhunter.commons.io.file.path;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 15:04
 */
public class DeletingPathVisitor extends SimpleFileVisitor<Path> {
  private final LinkOption[] linkOptions;

  public DeletingPathVisitor() {
    this.linkOptions = PathUtil.NOFOLLOW_LINK_OPTION_ARRAY;
  }

  public DeletingPathVisitor(LinkOption[] linkOptions) {
    if (linkOptions != null) {
      this.linkOptions = linkOptions;
    } else {
      this.linkOptions = PathUtil.NOFOLLOW_LINK_OPTION_ARRAY;
    }
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    return super.preVisitDirectory(dir, attrs);
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

    super.visitFile(file, attrs);

    // delete files and valid links, respecting linkOptions
    if (Files.exists(file, linkOptions)) {
      Files.deleteIfExists(file);
    }
    // invalid links will survive previous delete, different approach needed:
    if (Files.isSymbolicLink(file)) {
      try {
        // deleteIfExists does not work for this case
        Files.delete(file);
      } catch (final NoSuchFileException e) {
        // ignore
      }
    }
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
    return super.visitFileFailed(file, exc);
  }

  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
    if (PathUtil.isEmptyDirectory(dir)) {
      Files.deleteIfExists(dir);
    }
    return FileVisitResult.CONTINUE;
  }
}

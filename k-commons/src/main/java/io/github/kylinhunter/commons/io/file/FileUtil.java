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
package io.github.kylinhunter.commons.io.file;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.exception.ExCatcher;
import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.file.filter.DefaultPathFilter;
import io.github.kylinhunter.commons.io.file.filter.PathFilter;
import io.github.kylinhunter.commons.io.file.filter.SuffixPathFilter;
import io.github.kylinhunter.commons.io.file.path.PathUtil;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
@SuppressFBWarnings("PATH_TRAVERSAL_IN")
public class FileUtil {

  /**
   * @param pathname pathname
   * @return java.io.File
   * @title createFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 22:16
   */
  public static File newFile(String pathname) {
    return new File(pathname);
  }

  /**
   * @param uri uri
   * @return java.io.File
   * @title newFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 22:19
   */
  public static File newFile(URI uri) {
    return new File(uri);
  }

  /**
   * @param parent parent
   * @param child child
   * @return java.io.File
   * @title newFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 22:18
   */
  public static File newFile(String parent, String child) {
    return new File(parent, child);
  }

  /**
   * @param parent parent
   * @param child child
   * @return java.io.File
   * @title newFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 22:22
   */
  public static File newFile(File parent, String child) {
    return new File(parent, child);
  }

  /**
   * @param file file
   * @return java.io.File
   * @title delete
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:22
   */
  public static void delete(final File file) {
    try {
      Objects.requireNonNull(file, "file");
      Files.delete(file.toPath());
    } catch (IOException e) {
      throw new KIOException("delete error", e);
    }
  }

  /**
   * @param directory directory
   * @param createParent createParent
   * @param createFile createFile
   * @param isFile isFile
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 01:21
   */
  private static File getFileOrDir(
      final File directory,
      boolean createParent,
      boolean createFile,
      boolean isFile,
      final String... names) {
    Objects.requireNonNull(names, "names");
    File file = directory;
    for (final String name : names) {
      ExceptionChecker.checkNotEmpty(name, "name can't be empty");
      if (file == null) {
        file = newFile(name);
      } else {
        file = newFile(file, name);
      }
    }
    if (file.exists()) {
      if (isFile && file.isDirectory()) {
        throw new KIOException("not a file=>" + file.getAbsolutePath());
      } else if (!isFile && file.isFile()) {
        throw new KIOException("not a dir=>" + file.getAbsolutePath());
      }
      return file;
    } else {
      if (createParent || createFile) {

        if (!isFile) {
          forceMkdir(file);
        } else {
          forceMkdirParent(file);
        }
      }
      if (createFile) {
        try {
          //noinspection ResultOfMethodCallIgnored
          file.createNewFile();
        } catch (IOException e) {
          throw new KIOException("create file error", e);
        }
      }
    }
    return file;
  }

  /**
   * @param directory directory
   * @param createParent createParent
   * @param createFile createFile
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 01:22
   */
  public static File getFile(
      final File directory, boolean createParent, boolean createFile, final String... names) {
    return getFileOrDir(directory, createParent, createFile, true, names);
  }

  /**
   * @param dir dir
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:59
   */
  public static File getFile(File dir, final String... names) {
    return getFile(dir, false, false, names);
  }

  /**
   * @param dir dir
   * @param createParent createParent
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:59
   */
  public static File getFile(File dir, boolean createParent, final String... names) {
    return getFile(dir, createParent, false, names);
  }

  /**
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:59
   */
  public static File getFile(final String... names) {
    return getFile(null, false, false, names);
  }

  /**
   * @param createParent createParent
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:59
   */
  public static File getFile(boolean createParent, final String... names) {
    return getFile(null, createParent, false, names);
  }

  /**
   * @param createParent createParent
   * @param createFile createFile
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 01:00
   */
  public static File getFile(boolean createParent, boolean createFile, final String... names) {
    return getFile(null, createParent, createFile, names);
  }

  /**
   * @param dir dir
   * @param create create
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:59
   */
  public static File getDir(File dir, boolean create, final String... names) {
    return getFileOrDir(dir, create, false, false, names);
  }

  /**
   * @param dir dir
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:59
   */
  public static File getDir(File dir, final String... names) {
    return getDir(dir, false, names);
  }

  /**
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:59
   */
  public static File getDir(final String... names) {
    return getDir(null, false, names);
  }

  /**
   * @param createParent createParent
   * @param names names
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:59
   */
  public static File getDir(boolean createParent, final String... names) {
    return getDir(null, createParent, names);
  }

  /**
   * @param directory directory
   * @return boolean
   * @title isEmptyDirectory
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:01
   */
  public static boolean isEmptyDirectory(final File directory) {
    return PathUtil.isEmptyDirectory(directory.toPath());
  }

  /**
   * @param directory directory
   * @param extensions extensions
   * @param recursive recursive
   * @return java.util.Collection<java.io.File>
   * @title listFiles
   * @description
   * @author BiJi'an
   * @date 2023-04-22 16:17
   */
  public static Collection<File> listFiles(
      final File directory, final String[] extensions, final boolean recursive) {
    try (Stream<File> paths = streamFiles(directory, recursive, extensions)) {
      return paths.collect(Collectors.toList());
    }
  }

  /**
   * @param directory directory
   * @param recursive recursive
   * @param extensions extensions
   * @return java.util.stream.Stream<java.io.File>
   * @title streamFiles
   * @description
   * @author BiJi'an
   * @date 2023-04-22 15:27
   */
  public static Stream<File> streamFiles(
      final File directory, final boolean recursive, final String... extensions) {
    final PathFilter filter =
        extensions == null
            ? DefaultPathFilter.INSTANCE
            : DefaultPathFilter.INSTANCE.and(new SuffixPathFilter(extensions));
    int dep = recursive ? Integer.MAX_VALUE : 1;
    return PathUtil.walk(directory.toPath(), filter, dep, false).map(Path::toFile);
  }

  /**
   * @param file file
   * @return void
   * @title forceMkdirParent
   * @description
   * @author BiJi'an
   * @date 2023-04-22 15:09
   */
  public static void forceMkdirParent(final File file) {
    Objects.requireNonNull(file, "file");
    final File parent = file.getParentFile();
    if (parent == null) {
      return;
    }
    if (!parent.mkdirs() && !parent.isDirectory()) {
      throw new KIOException("Cannot create directory '" + parent + "'.");
    }
  }

  public static File forceMkdir(final File directory) {
    if ((directory != null) && (!directory.mkdirs() && !directory.isDirectory())) {
      throw new KIOException("Cannot create directory '" + directory + "'.");
    }
    return directory;
  }

  /**
   * @param file file
   * @return void
   * @title delte
   * @description
   * @author BiJi'an
   * @date 2023-02-26 14:33
   */
  public static void cleanDirectoryQuietly(File file) {
    try {
      cleanDirectory(file);
    } catch (KIOException e) {
      // ignore
    }
  }

  /**
   * @param directory directory
   * @return void
   * @throws KIOException KIOException
   * @title cleanDirectory
   * @description
   * @author BiJi'an
   * @date 2023-04-22 16:18
   */
  public static void cleanDirectory(final File directory) {

    try {
      File[] files = listFiles(directory, null);
      for (final File file : files) {
        forceDelete(file);
      }
    } catch (Exception e) {
      throw new KIOException("clean directory error", e);
    }
  }

  /**
   * @param file file
   * @return boolean
   * @title deleteQuietly
   * @description
   * @author BiJi'an
   * @date 2023-04-22 23:47
   */
  @SuppressWarnings("UnusedReturnValue")
  public static boolean deleteQuietly(final File file) {
    if (file == null) {
      return false;
    }
    try {
      if (file.isDirectory()) {
        cleanDirectory(file);
      }
    } catch (final Exception ignored) {
      // ignore
    }

    try {
      return file.delete();
    } catch (final Exception ignored) {
      return false;
    }
  }

  /**
   * @param directory directory
   * @param fileFilter fileFilter
   * @return java.io.File[]
   * @title listFiles
   * @description
   * @author BiJi'an
   * @date 2023-04-22 14:31
   */
  private static File[] listFiles(final File directory, final FileFilter fileFilter) {
    checkValidFile(directory, false, "directory");
    final File[] files =
        fileFilter == null ? directory.listFiles() : directory.listFiles(fileFilter);
    if (files == null) {
      throw new KIOException("Unknown I/O error listing contents of directory: " + directory);
    }
    return files;
  }

  /**
   * @param file file
   * @return void
   * @title forceDelete
   * @description
   * @author BiJi'an
   * @date 2023-04-22 14:31
   */
  public static void forceDelete(final File file) {
    Objects.requireNonNull(file, "file");
    PathUtil.delete(file.toPath(), PathUtil.EMPTY_LINK_OPTION_ARRAY);
  }

  /**
   * @param file file
   * @param isFile isFile
   * @return java.io.File
   * @title checkValidFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 14:31
   */
  public static File checkValidFile(File file, boolean isFile) {
    return checkValidFile(file, isFile, null);
  }

  /**
   * @param file file
   * @param isFile isFile
   * @return java.io.File
   * @title checkValidFile
   * @description
   * @author BiJi'an
   * @date 2023-04-22 23:31
   */
  public static File checkValidFile(File file, boolean isFile, String name) {
    name = StringUtil.defaultString(name) + " ";
    if (file == null) {
      throw new KIOException(name + "file can't be null");
    }

    if (file.exists()) {
      if (isFile && !file.isFile()) {
        throw new KIOException(name + "not a file :" + file.getAbsolutePath());
      } else if (!isFile && !file.isDirectory()) {
        throw new KIOException(name + "not a directory :" + file.getAbsolutePath());
      }
    } else {
      throw new KIOException(name + "no exist :" + file.getAbsolutePath());
    }
    return file;
  }

  /**
   * @param file file
   * @return java.io.FileInputStream
   * @title openInputStream
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:32
   */
  public static FileInputStream openInputStream(final File file) {
    return ExCatcher.run(
        () -> {
          Objects.requireNonNull(file, "file");
          return new FileInputStream(file);
        });
  }
}

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

import java.io.File;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
@Slf4j
public class TmpDirUtils {

  private static final File USER_DIR = UserDirUtils.getDir("k-tmp");
  private static final File JAVA_IO_TMPDIR = new File(System.getProperty("java.io.tmpdir"));
  private static final File SYS_DIR = new File(JAVA_IO_TMPDIR, "k-tmp");

  static {
    if (!JAVA_IO_TMPDIR.exists()) {
      log.error("java.io.tmpdir not exists={}", JAVA_IO_TMPDIR.getAbsolutePath());
    } else {
      log.info("java.io.tmpdir  exists ={}", JAVA_IO_TMPDIR.getAbsolutePath());
    }
  }

  /**
   * @return java.io.File
   * @title getUserDir
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File getUserDir() {
    return USER_DIR;
  }

  /**
   * @return java.io.File
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2024-01-05 17:08
   */

  public static File getSysDir() {
    return SYS_DIR;
  }

  /**
   * @param child child
   * @return java.io.File
   * @title getUserDir
   * @description getUserDir
   * @author BiJi'an
   * @date 2024-01-05 22:58
   */
  public static File getUserDir(String... child) {
    return FileUtil.getDir(USER_DIR, true, child);
  }


  /**
   * @param child the children file
   * @return java.io.File
   * @title getUserDir
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File getUserDir(boolean create, String... child) {

    return FileUtil.getDir(USER_DIR, create, child);
  }

  /**
   * @param child child
   * @return java.io.File
   * @title getSysDir
   * @description getSysDir
   * @author BiJi'an
   * @date 2024-01-05 17:15
   */

  public static File getSysDir(String... child) {
    return FileUtil.getDir(SYS_DIR, true, child);
  }

  public static File getSysDir(boolean create, String... child) {

    return FileUtil.getDir(SYS_DIR, create, child);
  }

  /**
   * @param child child
   * @return java.io.File
   * @title getUserFile
   * @description getUserFile
   * @author BiJi'an
   * @date 2024-01-05 17:13
   */

  public static File getUserFile(String... child) {
    return FileUtil.getFile(USER_DIR, true, false, child);
  }

  /**
   * @param createParent createParent
   * @param child        child
   * @return java.io.File
   * @title getUserFile
   * @description getUserFile
   * @author BiJi'an
   * @date 2024-01-05 17:17
   */
  public static File getUserFile(boolean createParent, String child) {
    return FileUtil.getFile(USER_DIR, createParent, false, child);
  }

  /**
   * @param child the children file
   * @return java.io.File
   * @title getUserDir
   * @description getUserFile
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File getUserFile(boolean createParent, boolean createFile, String child) {
    return FileUtil.getFile(USER_DIR, createParent, createFile, child);
  }

  /**
   * @param child child
   * @return java.io.File
   * @title getSysFile
   * @description getSysFile
   * @author BiJi'an
   * @date 2024-01-05 17:18
   */

  public static File getSysFile(String... child) {
    return FileUtil.getFile(SYS_DIR, true, false, child);
  }

  /**
   * @param createParent createParent
   * @param child        child
   * @return java.io.File
   * @title getSysFile
   * @description getSysFile
   * @author BiJi'an
   * @date 2024-01-05 17:17
   */
  public static File getSysFile(boolean createParent, String child) {
    return FileUtil.getFile(SYS_DIR, createParent, false, child);
  }

  /**
   * @param child the children file
   * @return java.io.File
   * @title getSysFile
   * @description getSysFile
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File getSysFile(boolean createParent, boolean createFile, String child) {
    return FileUtil.getFile(SYS_DIR, createParent, createFile, child);
  }
}

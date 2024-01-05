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
public class UserDirUtils {

  private static final File USER_DIR = new File(System.getProperty("user.dir"));

  static {
    if (!USER_DIR.exists()) {
      log.error("user.dir no exists={}", USER_DIR.getAbsolutePath());
    } else {
      log.info("user.dir  exists ={}", USER_DIR.getAbsolutePath());
    }
  }

  /**
   * @return java.io.File
   * @title getUserDir
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File get() {
    return USER_DIR;
  }

  /*
   * @description  getDir
   * @date  2022/1/24 2:12
   * @author  BiJi'an
   * @Param children
   * @return java.io.File
   */
  public static File getDir(String... child) {
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
  public static File getDir(boolean create, String... child) {

    return FileUtil.getDir(USER_DIR, create, child);
  }

  public static File getFile(String... child) {
    return FileUtil.getFile(USER_DIR, true, false, child);
  }

  public static File getFile(boolean createParent, String child) {
    return FileUtil.getFile(USER_DIR, createParent, false, child);
  }

  /**
   * @param child the children file
   * @return java.io.File
   * @title getUserDir
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File getFile(boolean createParent, boolean createFile, String child) {
    return FileUtil.getFile(USER_DIR, createParent, createFile, child);
  }
}

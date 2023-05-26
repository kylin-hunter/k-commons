package io.github.kylinhunter.commons.io.file;

import java.io.File;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class UserDirUtils {

  private static final File USER_DIR = new File(System.getProperty("user.dir"));
  private static final String PATH_OF_USER_DIR = USER_DIR.getAbsolutePath();
  private static final File USER_DIR_CONFIG = new File(System.getProperty("user.dir"), "config");
  private static final File USER_DIR_TMP = new File(System.getProperty("user.dir"), "tmp");

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

  /**
   * @return java.io.File
   * @title getUserDirTmp
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File getTmpDir() {
    return FileUtil.getDir(USER_DIR_TMP, true);

  }

  public static File getTmpDir(String... child) {
    return FileUtil.getDir(USER_DIR_TMP, true, child);
  }

  /**
   * @param child  the children file
   * @param create whether to create file or dir
   * @return java.io.File
   * @title getUserDirTmp
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File getTmpDir(boolean create, String child) {
    return FileUtil.getDir(USER_DIR_TMP, true, child);
  }

  public static File getTmpFile(String child) {
    return FileUtil.getFile(USER_DIR_TMP, true, false, child);

  }

  /**
   * @param child the children file
   * @return java.io.File
   * @title getUserDir
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:57
   */
  public static File getTmpFile(boolean createParent, String... child) {
    return FileUtil.getFile(USER_DIR_TMP, createParent, false, child);
  }

  public static File getTmpFile(boolean createParent, boolean createFile, String... child) {
    return FileUtil.getFile(USER_DIR_TMP, createParent, createFile, child);
  }

  /**
   * @return java.io.File
   * @title getExClassPath
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:58
   */
  public static File getDirConfig() {
    return FileUtil.getDir(USER_DIR_TMP, true);

  }

  /**
   * @param file the file to delete
   * @title deleteQuietly
   * @description
   * @author BiJi'an
   * @date 2022-01-01 01:58
   */
  public static void deleteQuietly(final File file) {

    if (file != null && file.getAbsolutePath().startsWith(PATH_OF_USER_DIR)) {
      FileUtil.deleteQuietly(file);
    }
  }
}

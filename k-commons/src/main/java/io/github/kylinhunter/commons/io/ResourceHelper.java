package io.github.kylinhunter.commons.io;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class ResourceHelper {

  /**
   * @param path path
   * @return java.io.InputStream
   * @title getInputStream
   * @description
   * @author BiJi'an
   * @date 2023-03-19 00:37
   */
  public static InputStream getInputStream(String path) {
    return getInputStream(path, PathType.CLASSPATH, false);
  }

  /**
   * @param path path
   * @param required required
   * @return java.io.InputStream
   * @title getInputStream
   * @description
   * @author BiJi'an
   * @date 2023-03-19 15:22
   */
  public static InputStream getInputStream(String path, boolean required) {
    return getInputStream(path, PathType.CLASSPATH, required);
  }

  /**
   * @param path path
   * @param priorityType priorityType
   * @return java.io.InputStream
   * @title getInputStream
   * @description
   * @author BiJi'an
   * @date 2023-03-19 14:55
   */
  public static InputStream getInputStream(String path, PathType priorityType) {
    return getInputStream(path, priorityType, false);
  }

  /**
   * @param path path
   * @return java.io.InputStream
   * @title getInputStream
   * @description
   * @author BiJi'an
   * @date 2022-01-01 02:11
   */
  @SuppressWarnings("resource")
  public static InputStream getInputStream(String path, PathType priorityType, boolean required) {

    try {
      InputStream inputStream;
      PathInfo pathInfo = new PathInfo(path);
      PathType pathType = pathInfo.getType();
      if (pathType == PathType.CLASSPATH) {
        inputStream = ResourceHelper.getInputStreamInClassPath(pathInfo.getPath());
      } else if (pathType == PathType.FILESYSTEM) {
        inputStream = FileUtil.openInputStream(pathInfo.getFile());
      } else {
        if (priorityType == PathType.FILESYSTEM) {
          inputStream = FileUtil.openInputStream(FileUtil.getFile(path));
          if (inputStream == null) {
            inputStream = ResourceHelper.getInputStreamInClassPath(path);
          }
        } else {
          inputStream = ResourceHelper.getInputStreamInClassPath(path);
          if (inputStream == null) {
            inputStream = FileUtil.openInputStream(FileUtil.getFile(path));
          }
        }
      }

      return Objects.requireNonNull(inputStream);
    } catch (Exception e) {
      if (required) {
        throw new KIOException("can't get inputStream :" + path, e);
      }
    }

    return null;
  }

  /**
   * @param classPath the path
   * @return java.io.InputStream
   * @title getInputStreamInClassPath
   * @description
   * @author BiJi'an
   * @date 2022-01-01 02:10
   */
  public static InputStream getInputStreamInClassPath(String classPath) {
    InputStream in = ResourceHelper.class.getClassLoader().getResourceAsStream(classPath);
    if (in != null) {
      return in;
    } else {
      return ResourceHelper.class.getResourceAsStream(classPath);
    }
  }

  /**
   * @param path path
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2022-11-23 15:17
   */
  public static File getFile(String path) {
    return getFile(path, true, PathType.CLASSPATH, false);
  }

  /**
   * @param path path
   * @param required required
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-02-26 17:46
   */
  public static File getFile(String path, boolean required) {
    return getFile(path, true, PathType.CLASSPATH, required);
  }

  /**
   * @param path path
   * @param priorityType priorityType
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-02-26 17:45
   */
  public static File getFile(String path, PathType priorityType) {
    return getFile(path, true, priorityType, false);
  }

  /**
   * @param path path
   * @param priorityType priorityType
   * @param required required
   * @return java.io.File
   * @title getFile
   * @description
   * @author BiJi'an
   * @date 2023-02-26 17:46
   */
  public static File getFile(String path, PathType priorityType, boolean required) {
    return getFile(path, true, priorityType, required);
  }

  /**
   * @param path path
   * @return java.io.File
   * @title getDir
   * @description
   * @author BiJi'an
   * @date 2023-02-26 17:39
   */
  public static File getDir(String path) {
    return getFile(path, false, PathType.CLASSPATH, false);
  }

  /**
   * @param path path
   * @return java.io.File
   * @title getDir
   * @description
   * @author BiJi'an
   * @date 2022-11-23 15:17
   */
  public static File getDir(String path, boolean required) {
    return getFile(path, false, PathType.CLASSPATH, required);
  }

  /**
   * @param path path
   * @param priorityType priorityType
   * @return java.io.File
   * @title getDir
   * @description
   * @author BiJi'an
   * @date 2023-02-26 17:44
   */
  public static File getDir(String path, PathType priorityType) {
    return getFile(path, false, priorityType, false);
  }

  /**
   * @param path path
   * @param priorityType priorityType
   * @return java.io.File
   * @title getDir
   * @description
   * @author BiJi'an
   * @date 2023-02-11 23:07
   */
  public static File getDir(String path, PathType priorityType, boolean required) {
    return getFile(path, false, priorityType, required);
  }

  /**
   * @param path path
   * @return java.lang.String
   * @title correctPath
   * @description
   * @author BiJi'an
   * @date 2022-01-21 00:52
   */
  private static File getFile(
      String path, boolean isFile, PathType priorityType, boolean required) {

    try {
      File file;
      PathInfo pathInfo = new PathInfo(path);
      PathType pathType = pathInfo.getType();

      if (pathType == PathType.CLASSPATH) {
        file = _getFileInClassPath(pathInfo.getPath());
      } else if (pathType == PathType.FILESYSTEM) {
        file = pathInfo.getFile();
      } else {
        if (priorityType == PathType.FILESYSTEM) {
          try {
            return FileUtil.checkValidFile(FileUtil.getFile(path), isFile);
          } catch (Exception e) {
            file = _getFileInClassPath(pathInfo.getPath());
          }

        } else {
          try {
            return FileUtil.checkValidFile(_getFileInClassPath(pathInfo.getPath()), isFile);
          } catch (Exception e) {
            file = FileUtil.getFile(path);
          }
        }
      }

      return FileUtil.checkValidFile(file, isFile);
    } catch (Exception e) {
      if (required) {
        throw new KIOException("can't get file :" + path, e);
      }
    }
    return null;
  }

  /**
   * @param classPath classPath
   * @return java.io.File
   * @title getFileInClassPath
   * @description
   * @author BiJi'an
   * @date 2023-02-26 17:44
   */
  public static File getFileInClassPath(String classPath) {
    return getFileInClassPath(classPath, true, false);
  }

  /**
   * @param classPath classPath
   * @return java.io.File
   * @title getFileInClassPath
   * @description
   * @author BiJi'an
   * @date 2022-01-01 02:12
   */
  public static File getFileInClassPath(String classPath, boolean required) {
    return getFileInClassPath(classPath, true, required);
  }

  /**
   * @param classPath classPath
   * @return java.io.File
   * @title getDirInClassPath
   * @description
   * @author BiJi'an
   * @date 2022-11-23 14:33
   */
  public static File getDirInClassPath(String classPath) {
    return getFileInClassPath(classPath, false, false);
  }

  /**
   * @param classPath classPath
   * @param required required
   * @return java.io.File
   * @title getDirInClassPath
   * @description
   * @author BiJi'an
   * @date 2023-02-26 17:47
   */
  public static File getDirInClassPath(String classPath, boolean required) {
    return getFileInClassPath(classPath, false, required);
  }

  /**
   * @param classPath classPath
   * @param isFile isFile
   * @param required required
   * @return java.io.File
   * @title _getFileInClassPath
   * @description
   * @author BiJi'an
   * @date 2023-04-22 14:28
   */
  private static File getFileInClassPath(String classPath, boolean isFile, boolean required) {

    try {
      File file = _getFileInClassPath(classPath);
      return FileUtil.checkValidFile(file, isFile);
    } catch (Exception e) {
      if (required) {
        throw new KIOException("can't get file :" + classPath, e);
      }
    }
    return null;
  }

  /**
   * @param classPath classPath
   * @return java.io.File
   * @title _getFileInClassPath
   * @description
   * @author BiJi'an
   * @date 2023-04-22 23:21
   */
  private static File _getFileInClassPath(String classPath) {

    try {
      URL url = ResourceHelper.class.getClassLoader().getResource(classPath);
      if (url == null) {
        url = ResourceHelper.class.getResource(classPath);
      }
      if (url != null) {
        File file = new File(url.getPath());
        if (file.exists()) {
          return file;
        } else {
          String path = url.toURI().getPath();
          if (path != null) {
            file = new File(path);
            if (file.exists()) {
              return file;
            }
          }
        }
      }
      return null;
    } catch (Exception e) {
      throw new KIOException("get file from classpath error", e);
    }
  }

  /**
   * @param path path
   * @return java.lang.String
   * @title getText
   * @description
   * @author BiJi'an
   * @date 2023-02-19 19:09
   */
  public static String getText(String path) {
    return getText(path, PathType.CLASSPATH, Charset.defaultCharset());
  }

  /**
   * @param path path
   * @param priorityType priorityType
   * @return java.lang.String
   * @title getText
   * @description
   * @author BiJi'an
   * @date 2023-02-19 19:08
   */
  public static String getText(String path, PathType priorityType) {
    return getText(path, priorityType, Charset.defaultCharset());
  }

  /**
   * @param path path
   * @param charset charset
   * @return java.lang.String
   * @title getText
   * @description
   * @author BiJi'an
   * @date 2023-02-19 19:10
   */
  public static String getText(String path, Charset charset) {
    return getText(path, PathType.CLASSPATH, charset);
  }

  /**
   * @param path path
   * @param priorityType priorityType
   * @param charset charset
   * @return java.lang.String
   * @title getText
   * @description
   * @author BiJi'an
   * @date 2023-02-19 19:08
   */
  public static String getText(String path, PathType priorityType, Charset charset) {
    try (InputStream inputStream = getInputStream(path, priorityType, true)) {
      return IOUtil.toString(inputStream, charset);
    } catch (IOException e) {
      throw new KIOException("get text error", e);
    }
  }

  /**
   * @author BiJi'an
   * @description
   * @date 2022-01-01 02:15
   */
  @Data
  private static class PathInfo {

    public static final String USER_DIR_TAG = "$user.dir$";
    public static final String PROTOCOL_FILE = "file:";
    public static final String CLASSPATH_TAG = "classpath:";
    private PathType type;
    private File file;
    private String path;
    private String originalPath;

    public PathInfo(String path) {
      this.originalPath = path;
      if (path.startsWith(CLASSPATH_TAG)) {
        this.type = PathType.CLASSPATH;
        this.path = path.substring(CLASSPATH_TAG.length());
      } else if (path.startsWith(PROTOCOL_FILE)) {
        this.type = PathType.FILESYSTEM;
        this.file = FileUtil.newFile(IOHelper.toURI(path));
        this.path = this.file.getAbsolutePath();
      } else if (path.startsWith(USER_DIR_TAG)) {
        this.type = PathType.FILESYSTEM;
        String replacePath = path.replace(USER_DIR_TAG, UserDirUtils.get().getAbsolutePath());
        this.file = FileUtil.newFile(replacePath);
        this.path = this.file.getAbsolutePath();
      } else {
        this.type = PathType.UNKNOWN;
        this.path = path;
      }
    }
  }

  /**
   * @author BiJi'an
   * @description
   * @date 2022-01-01 02:14
   */
  public enum PathType {
    CLASSPATH,
    FILESYSTEM,
    UNKNOWN
  }
}

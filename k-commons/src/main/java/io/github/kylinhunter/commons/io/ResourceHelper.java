package io.github.kylinhunter.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
public class ResourceHelper {
    public static final String USER_DIR_TAG = "$user.dir$";
    public static final String PROTOCOL_FILE = "file:";
    public static final String CLASSPATH_TAG = "classpath:";

    /**
     * @param path path
     * @return java.lang.String
     * @title correctPath
     * @description
     * @author BiJi'an
     * @date 2022-01-21 00:52
     */
    private static PathInfo toPathInfo(String path) {
        if (path.startsWith(CLASSPATH_TAG)) {
            return PathInfo.of(PathType.CLASSPATH, path.substring(CLASSPATH_TAG.length()));
        } else if (path.startsWith(PROTOCOL_FILE)) {
            return PathInfo.of(PathType.FILESYSTEM, path.replaceAll("file:[/]+", "/"));
        } else if (path.startsWith(USER_DIR_TAG)) {
            return PathInfo.of(PathType.FILESYSTEM, path.replace(USER_DIR_TAG, UserDirUtils.get().getAbsolutePath()));
        } else {
            return PathInfo.of(PathType.CLASSPATH, path);
        }

    }

    /**
     * @param path path
     * @return java.io.InputStream
     * @title getInputStream
     * @description
     * @author BiJi'an
     * @date 2022-01-01 02:11
     */
    public static InputStream getInputStream(String path) {
        PathInfo pathInfo = toPathInfo(path);
        PathType pathType = pathInfo.getPathType();
        if (pathType == PathType.CLASSPATH) {
            return ResourceHelper.getInputStreamInClassPath(pathInfo.getPath());
        } else {
            File file = new File(pathInfo.getPath());
            try {
                if (file.exists() && file.isFile()) {
                    return new FileInputStream(file);
                }
            } catch (FileNotFoundException e) {
                throw new KIOException("invalid file " + file.getAbsolutePath(), e);
            }
            return null;
        }
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
        return _getFile(path, true);

    }

    /**
     * @param path path
     * @return java.io.File
     * @title getDir
     * @description
     * @author BiJi'an
     * @date 2022-11-23 15:17
     */
    public static File getDir(String path) {
        return _getFile(path, false);
    }

    /**
     * @param path path
     * @return java.lang.String
     * @title correctPath
     * @description
     * @author BiJi'an
     * @date 2022-01-21 00:52
     */
    private static File _getFile(String path, boolean isFile) {
        PathInfo pathInfo = toPathInfo(path);
        PathType pathType = pathInfo.getPathType();
        File file;
        if (pathType == PathType.CLASSPATH) {
            return _getFileInClassPath(pathInfo.getPath(), isFile);
        } else {
            file = new File(pathInfo.getPath());
            if (file.exists()) {
                if (isFile) {
                    if (!file.isFile()) {
                        throw new KIOException("not a file ");
                    }

                } else {
                    if (!file.isDirectory()) {
                        throw new KIOException("not a dir ");
                    }

                }
                return file;
            }
        }
        return null;

    }

    /**
     * @param classPath classPath
     * @param isFile    isFile
     * @return java.io.File
     * @title _getFileInClassPath
     * @description
     * @author BiJi'an
     * @date 2022-11-23 15:17
     */
    private static File _getFileInClassPath(String classPath, boolean isFile) {
        URL url = ResourceHelper.class.getClassLoader().getResource(classPath);
        if (url == null) {
            url = ResourceHelper.class.getResource(classPath);
        }
        return getFile(url, isFile);
    }

    /**
     * @param classPath classPath
     * @return java.io.File
     * @title getFileInClassPath
     * @description
     * @author BiJi'an
     * @date 2022-01-01 02:12
     */

    public static File getFileInClassPath(String classPath) {
        return _getFileInClassPath(classPath, true);
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
        return _getFileInClassPath(classPath, false);
    }

    /**
     * @param url url
     * @return java.io.File
     * @title getFile
     * @description
     * @author BiJi'an
     * @date 2022-01-01 02:11
     */
    private static File getFile(URL url, boolean isFile) {
        File file = null;
        try {
            if (url != null) {
                file = new File(url.getPath());
                if (!file.exists()) {
                    file = new File(url.toURI().getPath());
                }
            }
        } catch (Exception e) {
            log.warn("get File error " + url, e);
        }

        if (file != null && file.exists()) {
            if (isFile) {
                if (!file.isFile()) {
                    throw new KIOException("not a file ");
                }

            } else {
                if (!file.isDirectory()) {
                    throw new KIOException("not a dir ");
                }
            }
            return file;
        }
        return null;

    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022-01-01 02:15
     **/
    @Data
    @AllArgsConstructor
    private static class PathInfo {
        private PathType pathType;
        private String path;

        public static PathInfo of(PathType pathType, String path) {
            return new PathInfo(pathType, path);
        }

    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022-01-01 02:14
     **/
    private enum PathType {
        CLASSPATH, FILESYSTEM
    }
}

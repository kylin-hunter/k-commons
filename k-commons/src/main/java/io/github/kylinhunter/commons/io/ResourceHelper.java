package io.github.kylinhunter.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.file.FileUtils;
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
    private static PathInfo toPathInfo(String path, PathType defaultType) {
        if (path.startsWith(CLASSPATH_TAG)) {
            return PathInfo.of(PathType.CLASSPATH, path.substring(CLASSPATH_TAG.length()));
        } else if (path.startsWith(PROTOCOL_FILE)) {
            return PathInfo.of(PathType.FILESYSTEM, IOHelper.toURI(path));
        } else if (path.startsWith(USER_DIR_TAG)) {
            String filePath = path.replace(USER_DIR_TAG, UserDirUtils.get().getAbsolutePath());
            return PathInfo.of(PathType.FILESYSTEM, new File(filePath));
        } else {
            return PathInfo.of(defaultType, path);
        }

    }

    public static InputStream getInputStream(String path) {
        return getInputStream(path, PathType.CLASSPATH);
    }

    /**
     * @param path path
     * @return java.io.InputStream
     * @title getInputStream
     * @description
     * @author BiJi'an
     * @date 2022-01-01 02:11
     */
    public static InputStream getInputStream(String path, PathType defaultType) {
        PathInfo pathInfo = toPathInfo(path, defaultType);
        PathType pathType = pathInfo.getPathType();
        if (pathType == PathType.CLASSPATH) {
            return ResourceHelper.getInputStreamInClassPath(pathInfo.getPath());
        } else {
            File file = pathInfo.file;
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
        return _getFile(path, true, PathType.CLASSPATH);

    }

    /**
     * @param path path
     * @param defaultPathType defaultPathType
     * @return java.io.File
     * @title getFile
     * @description
     * @author BiJi'an
     * @date 2023-02-11 23:08
     */
    public static File getFile(String path, PathType defaultPathType) {
        return _getFile(path, true, defaultPathType);

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
        return _getFile(path, false, PathType.CLASSPATH);
    }

    /**
     * @param path            path
     * @param defaultPathType defaultPathType
     * @return java.io.File
     * @title getDir
     * @description
     * @author BiJi'an
     * @date 2023-02-11 23:07
     */
    public static File getDir(String path, PathType defaultPathType) {
        return _getFile(path, false, defaultPathType);
    }

    /**
     * @param path path
     * @return java.lang.String
     * @title correctPath
     * @description
     * @author BiJi'an
     * @date 2022-01-21 00:52
     */
    private static File _getFile(String path, boolean isFile, PathType defaultPathType) {
        PathInfo pathInfo = toPathInfo(path, defaultPathType);
        PathType pathType = pathInfo.getPathType();
        if (pathType == PathType.CLASSPATH) {
            return _getFileInClassPath(pathInfo.getPath(), isFile);
        } else {
            File file = pathInfo.file;
            return FileUtils.check(file, isFile);

        }

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
        File file = getFile(url);
        return FileUtils.check(file, isFile);

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
    private static File getFile(URL url) {
        File file = null;
        try {
            if (url != null) {
                file = new File(url.getPath());
                if (!file.exists()) {
                    String path = url.toURI().getPath();
                    if (path != null) {
                        file = new File(path);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("get File error " + url, e);
        }
        return file;

    }

    /**
     * @param path path
     * @param defaultType defaultType
     * @return java.lang.String
     * @title getText
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:08
     */
    public static String getText(String path, PathType defaultType) {
        return getText(path, defaultType, Charset.defaultCharset());
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
     * @param path    path
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
     * @param path        path
     * @param defaultType defaultType
     * @param charset     charset
     * @return java.lang.String
     * @title getText
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:08
     */
    public static String getText(String path, PathType defaultType, Charset charset) {
        InputStream inputStream = getInputStream(path, defaultType);
        if (inputStream == null) {
            throw new KIOException("invalide path" + path);
        }
        try {
            return IOUtils.toString(inputStream, charset);
        } catch (IOException e) {
            throw new KIOException("read path error" + path, e);

        }
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
        private File file;
        private String path;

        public static PathInfo of(PathType pathType, String path) {
            return new PathInfo(pathType, null, path);
        }

        public static PathInfo of(PathType pathType, File file) {
            return new PathInfo(pathType, file, null);
        }

        public static PathInfo of(PathType pathType, URI uri) {
            return new PathInfo(pathType, new File(uri), uri.getPath());
        }

    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022-01-01 02:14
     **/
    public enum PathType {
        CLASSPATH, FILESYSTEM
    }
}

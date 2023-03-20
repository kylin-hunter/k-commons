package io.github.kylinhunter.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/

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
            return PathInfo.classPath(path.substring(CLASSPATH_TAG.length()));
        } else if (path.startsWith(PROTOCOL_FILE)) {
            return PathInfo.filePath(IOHelper.toURI(path));
        } else if (path.startsWith(USER_DIR_TAG)) {
            return PathInfo.filePath(path.replace(USER_DIR_TAG, UserDirUtils.get().getAbsolutePath()));
        } else {
            return PathInfo.unknownPath(path);
        }

    }

    /**
     * @param path path
     * @return java.io.InputStream
     * @title getInputStream
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:37
     */
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
    public static InputStream getInputStream(String path, PathType priorityType) {
        PathInfo pathInfo = toPathInfo(path);
        PathType pathType = pathInfo.getType();
        if (pathType == PathType.CLASSPATH) {
            return ResourceHelper.getInputStreamInClassPath(pathInfo.getPath());
        } else if (pathType == PathType.FILESYSTEM) {
            return IOHelper.getFileInputStream(pathInfo.file);
        } else {
            InputStream inputStream;
            if (priorityType == PathType.FILESYSTEM) {
                inputStream = IOHelper.getFileInputStream(new File(pathInfo.getPath()));
                if (inputStream == null) {
                    return ResourceHelper.getInputStreamInClassPath(pathInfo.getPath());
                }
            } else {
                inputStream = ResourceHelper.getInputStreamInClassPath(pathInfo.getPath());
                if (inputStream == null) {
                    return IOHelper.getFileInputStream(new File(pathInfo.getPath()));
                }
            }
            return inputStream;

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
        return _getFile(path, true, PathType.CLASSPATH, false);

    }

    /**
     * @param path     path
     * @param required required
     * @return java.io.File
     * @title getFile
     * @description
     * @author BiJi'an
     * @date 2023-02-26 17:46
     */
    public static File getFile(String path, boolean required) {
        return _getFile(path, true, PathType.CLASSPATH, required);

    }

    /**
     * @param path         path
     * @param priorityType priorityType
     * @return java.io.File
     * @title getFile
     * @description
     * @author BiJi'an
     * @date 2023-02-26 17:45
     */
    public static File getFile(String path, PathType priorityType) {
        return _getFile(path, true, priorityType, false);

    }

    /**
     * @param path            path
     * @param priorityType priorityType
     * @param required        required
     * @return java.io.File
     * @title getFile
     * @description
     * @author BiJi'an
     * @date 2023-02-26 17:46
     */
    public static File getFile(String path, PathType priorityType, boolean required) {
        return _getFile(path, true, priorityType, required);

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
        return _getFile(path, false, PathType.CLASSPATH, false);

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
        return _getFile(path, false, PathType.CLASSPATH, required);
    }

    /**
     * @param path            path
     * @param priorityType priorityType
     * @return java.io.File
     * @title getDir
     * @description
     * @author BiJi'an
     * @date 2023-02-26 17:44
     */
    public static File getDir(String path, PathType priorityType) {
        return _getFile(path, false, priorityType, false);
    }

    /**
     * @param path            path
     * @param priorityType priorityType
     * @return java.io.File
     * @title getDir
     * @description
     * @author BiJi'an
     * @date 2023-02-11 23:07
     */
    public static File getDir(String path, PathType priorityType, boolean required) {
        return _getFile(path, false, priorityType, required);
    }

    /**
     * @param path path
     * @return java.lang.String
     * @title correctPath
     * @description
     * @author BiJi'an
     * @date 2022-01-21 00:52
     */
    private static File _getFile(String path, boolean isFile, PathType priorityType, boolean required) {
        PathInfo pathInfo = toPathInfo(path);
        PathType pathType = pathInfo.getType();
        if (pathType == PathType.CLASSPATH) {
            return _getFileInClassPath(pathInfo.getPath(), isFile, required);
        } else if (pathType == PathType.FILESYSTEM) {
            return FileUtil.check(pathInfo.file, isFile, required);
        } else {
            if (priorityType == PathType.FILESYSTEM) {
                File file = FileUtil.check(new File(pathInfo.path), isFile, false);
                if (file == null) {
                    return _getFileInClassPath(pathInfo.getPath(), isFile, required);
                }
                return file;
            } else {
                File file = _getFileInClassPath(pathInfo.getPath(), isFile, false);
                if (file == null) {
                    return FileUtil.check(new File(pathInfo.path), isFile, required);
                }
                return file;
            }

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
    private static File _getFileInClassPath(String classPath, boolean isFile, boolean required) {
        URL url = ResourceHelper.class.getClassLoader().getResource(classPath);
        if (url == null) {
            url = ResourceHelper.class.getResource(classPath);
        }
        File file = getFile(url);
        return FileUtil.check(file, isFile, required);

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
        return _getFileInClassPath(classPath, true, false);
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
        return _getFileInClassPath(classPath, true, required);
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
        return _getFileInClassPath(classPath, false, false);
    }

    /**
     * @param classPath classPath
     * @param required  required
     * @return java.io.File
     * @title getDirInClassPath
     * @description
     * @author BiJi'an
     * @date 2023-02-26 17:47
     */
    public static File getDirInClassPath(String classPath, boolean required) {
        return _getFileInClassPath(classPath, false, required);
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
            throw new GeneralException("get File error=> " + url, e);

        }
        return file;

    }

    /**
     * @param path        path
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
     * @param priorityType priorityType
     * @param charset     charset
     * @return java.lang.String
     * @title getText
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:08
     */
    public static String getText(String path, PathType priorityType, Charset charset) {
        try (InputStream inputStream = getInputStream(path, priorityType)) {
            if (inputStream == null) {
                throw new KIOException("invalid path" + path);
            }
            return IOHelper.toString(inputStream, charset);
        } catch (IOException e) {
            throw new KIOException("get text error", e);
        }

    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022-01-01 02:15
     **/
    @Data
    private static class PathInfo {
        private PathType type;
        private File file;
        private String path;

        public PathInfo(PathType type, String path) {
            this.type = type;
            this.path = path;
        }

        public PathInfo(PathType type, File file) {
            this.type = type;
            this.file = file;
            this.path = file.getAbsolutePath();

        }

        public static PathInfo classPath(String path) {
            return new PathInfo(PathType.CLASSPATH, path);
        }

        public static PathInfo filePath(String path) {
            return new PathInfo(PathType.FILESYSTEM, new File(path));
        }

        public static PathInfo filePath(URI uri) {
            return new PathInfo(PathType.FILESYSTEM, new File(uri));
        }

        public static PathInfo unknownPath(String path) {
            return new PathInfo(null, path);
        }

    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022-01-01 02:14
     **/
    public enum PathType {
        CLASSPATH, FILESYSTEM;
    }
}

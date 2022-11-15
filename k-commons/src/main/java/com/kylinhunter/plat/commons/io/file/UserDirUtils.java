package com.kylinhunter.plat.commons.io.file;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.kylinhunter.plat.commons.exception.inner.KIOException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class UserDirUtils {
    private static final File USER_DIR = new File(System.getProperty("user.dir"));
    private static final String PATH_OF_USER_DIR = USER_DIR.getAbsolutePath();
    private static final String USER_DIR_CONFIG = "configurationBuilder";
    private static final String USER_DIR_TMP = "tmp";
    private static final String USER_DIR_TMP_JAVA = USER_DIR_TMP + "/src/main/java";
    private static final String USER_DIR_TMP_RESOURCE = USER_DIR_TMP + "/src/main/resources";

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
     * @Param child
     * @return java.io.File
     */
    public static File getDir(String child) {
        return getDir(child, true);
    }

    /**
     * @param child the child file
     * @return java.io.File
     * @title getUserDir
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:57
     */
    public static File getDir(String child, boolean create) {
        File file = new File(USER_DIR, child);
        if (file.exists()) {
            if (file.isFile()) {
                throw new KIOException("not a dir" + file.getAbsolutePath());
            }
        } else {
            if (create) {
                FileUtil.forceMkdir(file);
            }
        }
        return file;
    }

    public static File getFile(String child) {
        return getFile(child, true);
    }

    /**
     * @param child the child file
     * @return java.io.File
     * @title getUserDir
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:57
     */
    public static File getFile(String child, boolean createParent) {
        File file = new File(USER_DIR, child);
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new KIOException("not a file" + file.getAbsolutePath());
            }
        } else {
            if (!file.getParentFile().exists() && createParent) {
                FileUtil.forceMkdir(file.getParentFile());
            }
        }
        return file;
    }

    /**
     * @return java.io.File
     * @title getUserDirTmp
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:57
     */
    public static File getTmpDir() {
        return getDir(USER_DIR_TMP, true);
    }

    public static File getTmpDir(String child) {
        return getDir(USER_DIR_TMP + File.separator + child, true);
    }

    /**
     * @param child  the child file
     * @param create whether to create file or dir
     * @return java.io.File
     * @title getUserDirTmp
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:57
     */
    public static File getTmpDir(String child, boolean create) {
        return getDir(USER_DIR_TMP + File.separator + child, create);

    }

    public static File getTmpFile(String child) {
        return getFile(USER_DIR_TMP + File.separator + child, true);
    }

    /**
     * @param child the child file
     * @return java.io.File
     * @title getUserDir
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:57
     */
    public static File getTmpFile(String child, boolean createParent) {
        return getFile(USER_DIR_TMP + File.separator + child, createParent);

    }

    /**
     * @return java.io.File
     * @title getUserDirTmpJava
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:57
     */
    public static File getTmpDirJava() {
        return getDir(USER_DIR_TMP_JAVA, true);
    }

    /**
     * @return java.io.File
     * @title getUserDirTmpResource
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:58
     */
    public static File getTmpDirResource() {
        return getDir(USER_DIR_TMP_RESOURCE, true);
    }

    /**
     * @return java.io.File
     * @title getExClassPath
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:58
     */
    public static File getDirConfig() {
        return getDir(USER_DIR_CONFIG, true);

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
            FileUtils.deleteQuietly(file);
        }

    }

    public static File getUserDirTmpResource() {

        return getDir(USER_DIR_TMP_RESOURCE, true);

    }

}

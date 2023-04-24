package io.github.kylinhunter.commons.io.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Objects;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.path.PathUtil;
import io.github.kylinhunter.commons.strings.StringUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/

public class FileUtil {

    public static void forceMkdirParent(final File file) {
        Objects.requireNonNull(file, "file");
        final File parent = file.getParentFile();
        if (parent == null) {
            return;
        }
        if ((parent != null) && (!parent.mkdirs() && !parent.isDirectory())) {
            throw new KIOException("Cannot create directory '" + parent + "'.");
        }
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
    public static void cleanDirectory(final File directory) throws KIOException {

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
     * @param directory  directory
     * @param fileFilter fileFilter
     * @return java.io.File[]
     * @title listFiles
     * @description
     * @author BiJi'an
     * @date 2023-04-22 14:31
     */
    private static File[] listFiles(final File directory, final FileFilter fileFilter) {
        checkValidFile(directory, false, "directory");
        final File[] files = fileFilter == null ? directory.listFiles() : directory.listFiles(fileFilter);
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
        try {
            PathUtil.delete(file.toPath(), PathUtil.EMPTY_LINK_OPTION_ARRAY);
        } catch (final IOException e) {
            throw new KIOException("Cannot delete file: " + file, e);
        }
    }

    /**
     * @param file   file
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
     * @param file   file
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

}

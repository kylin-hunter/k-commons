package io.github.kylinhunter.commons.io.file;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

import io.github.kylinhunter.commons.exception.embed.KIOException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/

public class FileUtil {
    @Deprecated
    private FileUtil() {

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
        if (file != null && file.isDirectory()) {
            try {
                FileUtils.cleanDirectory(file);
            } catch (IOException ignore) {
                // ignore
            }
        }

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
    public static File checkValidFile(File file, boolean isFile) {

        Objects.requireNonNull(file);
        if (file.exists()) {
            if (isFile && !file.isFile()) {
                throw new KIOException(" not a file " + file.getAbsolutePath());
            } else if (!isFile && !file.isDirectory()) {
                throw new KIOException(" not a directory " + file.getAbsolutePath());
            }
        } else {
            throw new KIOException(file.getAbsolutePath() + "  no exist ");
        }
        return file;
    }

}

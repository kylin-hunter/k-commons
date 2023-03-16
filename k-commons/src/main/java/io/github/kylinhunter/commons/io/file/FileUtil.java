package io.github.kylinhunter.commons.io.file;

import java.io.File;
import java.io.IOException;

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
     * @param file file
     * @return void
     * @title mustFile
     * @description
     * @author BiJi'an
     * @date 2023-01-08 01:02
     */
    public static File check(File file, boolean isFile, boolean required) {
        if (file != null && file.exists()) {
            if (isFile) {
                if (file.isFile()) {
                    return file;

                }
            } else {
                if (file.isDirectory()) {
                    return file;
                }
            }

        }
        if (required) {
            throw new KIOException("can't be null");
        }
        return null;
    }

}

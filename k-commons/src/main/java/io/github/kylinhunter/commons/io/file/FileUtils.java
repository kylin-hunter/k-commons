package io.github.kylinhunter.commons.io.file;

import java.io.File;

import io.github.kylinhunter.commons.exception.embed.KIOException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class FileUtils extends org.apache.commons.io.FileUtils {
    @Deprecated
    private FileUtils() {

    }

    /**
     * @param file file
     * @return void
     * @title mustFile
     * @description
     * @author BiJi'an
     * @date 2023-01-08 01:02
     */
    public static void checkFile(File file) {
        if (file == null) {
            throw new KIOException("file can't be null");
        } else {
            if (!file.isFile()) {
                throw new KIOException("not a file :" + file.getAbsolutePath());
            }
        }
    }

}

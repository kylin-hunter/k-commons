package io.github.kylinhunter.commons.io.file;

import java.io.File;
import java.io.IOException;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
public class FileUtils extends org.apache.commons.io.FileUtils {
    @Deprecated
    private FileUtils() {

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
                cleanDirectory(file);
                log.info("clean dir success:" + file.getAbsolutePath());
            } catch (IOException e) {
                log.error("clean dir error:" + file.getAbsolutePath());
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

package io.github.kylinhunter.commons.io.file;

import java.io.File;

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
     * @title mustFile
     * @description
     * @author BiJi'an
     * @date 2023-01-08 01:02
     */
    public static File check(File file, boolean isFile) {
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
        return null;
    }

}

package com.kylinhunter.plat.commons.io.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.kylinhunter.plat.commons.exception.inner.KIOException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class FileUtil {
    /**
     * @param directory the dir
     * @title forceMkdir
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:58
     */
    public static void forceMkdir(final File directory) {
        try {
            FileUtils.forceMkdir(directory);
        } catch (IOException e) {
            throw new KIOException("forceMkdir  error", e);
        }
    }
}

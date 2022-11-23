package io.github.kylinhunter.commons.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-30 18:25
 **/
public class FilenameUtils {
    private final static Map<String, String> ACTUAL_EXTENSIONS = Maps.newHashMap();

    static {
        ACTUAL_EXTENSIONS.put("z", "tar.z");
        ACTUAL_EXTENSIONS.put("gz", "tar.gz");
    }

    public static String getExtension(final String fileName) {

        String extension = org.apache.commons.io.FilenameUtils.getExtension(fileName);
        if (extension != null && extension.length() > 0) {
            String actualExtension = ACTUAL_EXTENSIONS.get(extension.toLowerCase());
            if (actualExtension != null) {
                if (fileName.toLowerCase().endsWith(actualExtension)) {
                    return actualExtension;
                }
            }

            return extension.toLowerCase();

        }
        return StringUtils.EMPTY;
    }
}

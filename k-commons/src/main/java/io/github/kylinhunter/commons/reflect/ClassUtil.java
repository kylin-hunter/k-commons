package io.github.kylinhunter.commons.reflect;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-21 01:05
 **/
public class ClassUtil {

    /**
     * @param className className
     * @return java.lang.String
     * @title getSimpleName
     * @description
     * @author BiJi'an
     * @date 2023-02-21 01:07
     */
    public static String getSimpleName(String className) {
        int i = className.lastIndexOf(".");
        if (i > 0) {
            return className.substring(i + 1);
        }
        return "";

    }
}

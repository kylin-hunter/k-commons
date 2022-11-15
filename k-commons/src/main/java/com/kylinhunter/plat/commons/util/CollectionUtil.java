package com.kylinhunter.plat.commons.util;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
public class CollectionUtil {

    /**
     * @param results results
     * @return java.util.List<T>
     * @title merge body
     * @description
     * @author BiJi'an
     * @date 2022/1/1 8:30
     */
    @SafeVarargs
    public static <T> List<T> merge(List<T>... results) {

        List<T> resultAll = null;
        for (List<T> result : results) {
            if (result != null && result.size() > 0) {
                if (resultAll == null) {
                    resultAll = result;
                } else {
                    resultAll.addAll(result);
                }
            }
        }
        return resultAll;
    }
}

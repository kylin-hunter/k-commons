package io.github.kylinhunter.commons.collections;

import java.util.Collections;
import java.util.List;



/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/

public class CollectionUtils {

    /**
     * @param results results
     * @return java.util.List<T>
     * @title merge body
     * @description
     * @author BiJi'an
     * @date 2022/1/1 8:30
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> merge(boolean reuse, List<T>... results) {

        List<T> dist = reuse ? null : ListUtils.newArrayList();
        for (List<T> result : results) {
            if (result != null && result.size() > 0) {
                if (dist == null) {
                    dist = result;
                } else {
                    dist.addAll(result);
                }
            }
        }
        return dist != null ? dist : Collections.EMPTY_LIST;
    }
}

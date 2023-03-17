package io.github.kylinhunter.commons.collections;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 22:09
 **/
public class Iterators {

    /**
     * @param addTo    addTo
     * @param iterator iterator
     * @return boolean
     * @title addAll
     * @description
     * @author BiJi'an
     * @date 2023-03-19 22:10
     */
    public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
        if (addTo != null && iterator != null) {
            boolean wasModified = false;
            while (iterator.hasNext()) {
                wasModified |= addTo.add(iterator.next());
            }
            return wasModified;
        }
        return false;

    }
}

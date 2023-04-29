package io.github.kylinhunter.commons.collections;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class ListUtils {
    private ListUtils() {
    }

    /**
     * @return java.util.ArrayList<E>
     * @title newArrayList
     * @description
     * @author BiJi'an
     * @date 2023-03-19 22:51
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
        ExceptionChecker.checkNonnegative(initialArraySize, "initialArraySize");
        return new ArrayList(initialArraySize);
    }

    /**
     * @param elements elements
     * @return java.util.ArrayList<E>
     * @title newArrayList
     * @description
     * @author BiJi'an
     * @date 2023-03-19 22:51
     */
    @SuppressWarnings("unchecked")
    public static <E> ArrayList<E> newArrayList(E... elements) {
        if (elements != null) {
            int length = elements.length;
            int capacity = 5 + length + (length / 10);
            ArrayList<E> list = new ArrayList<>(capacity);
            Collections.addAll(list, elements);
            return list;
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        if (elements instanceof Collection) {
            return new ArrayList<>((Collection<? extends E>) elements);
        } else {
            ArrayList<E> set = newArrayList();
            Iterators.addAll(set, elements.iterator());
            return set;
        }
    }

}

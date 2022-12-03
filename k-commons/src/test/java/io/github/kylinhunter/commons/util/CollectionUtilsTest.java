package io.github.kylinhunter.commons.util;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CollectionUtilsTest {

    @Test
    void merge() {
        List<Integer> l1 = Lists.newArrayList(1, 2);
        List<Integer> l2 = Lists.newArrayList(3, 4);
        List<Integer> mergeList = CollectionUtils.merge(true, l1, l2);

        Assertions.assertEquals(4, l1.size());
        Assertions.assertEquals(2, l2.size());
        Assertions.assertEquals(4, mergeList.size());

        l1 = Lists.newArrayList(1, 2);
        l2 = Lists.newArrayList(3, 4);
        mergeList = CollectionUtils.merge(false, l1, l2);

        Assertions.assertEquals(2, l1.size());
        Assertions.assertEquals(2, l2.size());
        Assertions.assertEquals(4, mergeList.size());

    }
}
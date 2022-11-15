package com.kylinhunter.plat.commons.util;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilTest {

    @Test
    void merge() {
        List<String> dist1 = Lists.newArrayList("1", "2");
        List<String> dist2 = Lists.newArrayList("3", "4");
        List<String> dist = CollectionUtil.merge(dist1, dist2);
        assertEquals(4, dist.size());
    }
}
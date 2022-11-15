package com.kylinhunter.plat.commons.exception.info;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ErrInfoManagerTest {

    @Test
    void test() {
        ErrInfoManager.println();

        Assertions.assertEquals("UNKNOWN", ErrInfoManager.getDefaultMsg(-1));
        Assertions.assertEquals("FORMAT", ErrInfoManager.getDefaultMsg(100000001));
        Assertions.assertEquals("INIT", ErrInfoManager.getDefaultMsg(100000002));
        Assertions.assertEquals("INTERNAL", ErrInfoManager.getDefaultMsg(100000003));
        Assertions.assertEquals("IO", ErrInfoManager.getDefaultMsg(100000004));
        Assertions.assertEquals("PARAM", ErrInfoManager.getDefaultMsg(100000005));
        Assertions.assertEquals("BIZ", ErrInfoManager.getDefaultMsg(100010001));
        Assertions.assertEquals("DB", ErrInfoManager.getDefaultMsg(100020001));
        Assertions.assertEquals("DB_NO_EXIST", ErrInfoManager.getDefaultMsg(100020002));
    }

}
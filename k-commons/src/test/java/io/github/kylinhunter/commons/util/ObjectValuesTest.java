package io.github.kylinhunter.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.date.DateUtils;

class ObjectValuesTest {

    @Test
    void test() {

        Object obj = true;
        assertEquals(true, ObjectValues.getBoolean(obj));
        assertEquals(false, ObjectValues.getBoolean(null, false));

        obj = 0.7d;
        assertEquals(0.7d, ObjectValues.getDouble(obj));
        assertEquals(0.8d, ObjectValues.getDouble(null, 0.8d));

        obj = 0.7f;
        assertEquals(0.7f, ObjectValues.getFloat(obj));
        assertEquals(0.8f, ObjectValues.getFloat(null, 0.8f));

        obj = 7;
        assertEquals(7, ObjectValues.getInt(obj));
        assertEquals(8, ObjectValues.getInt(null, 8));

        obj = 7L;
        assertEquals(7L, ObjectValues.getLong(obj));
        assertEquals(8L, ObjectValues.getLong(null, 8L));

        obj = DateUtils.parseWithDate("2000-01-01");
        assertEquals(DateUtils.parseWithDate("2000-01-01"), ObjectValues.getLocalDateTime(obj));

        obj = "test";
        assertEquals("test", ObjectValues.getString(obj));

    }

}
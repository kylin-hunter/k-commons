package com.kylinhunter.plat.commons.util;

import java.time.LocalDateTime;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.kylinhunter.plat.commons.util.date.DateUtils;

/**
 * @description: 从Object对象获取一些基础类型数据
 * @author: BiJi'an
 * @create: 2021/7/29
 **/
public class ObjectValues {

    public static boolean getBoolean(Object obj) {
        return getBoolean(obj, false);
    }

    public static boolean getBoolean(Object obj, boolean defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj);
        }
        return BooleanUtils.toBoolean(obj.toString());

    }

    public static int getInt(Object obj) {
        return getInt(obj, 0);
    }

    public static int getInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else {
            return NumberUtils.toInt(String.valueOf(obj), defaultValue);
        }
    }

    public static long getLong(Object obj) {
        return getLong(obj, 0);
    }

    public static long getLong(Object obj, long defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        } else {
            return NumberUtils.toLong(String.valueOf(obj), defaultValue);
        }
    }

    public static double getDouble(Object obj) {
        return getDouble(obj, 0);
    }

    public static double getDouble(Object obj, double defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        } else {
            return NumberUtils.toDouble(String.valueOf(obj), defaultValue);
        }
    }

    public static float getFloat(Object obj) {
        return getFloat(obj, 0);
    }

    public static float getFloat(Object obj, float defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        } else {
            return NumberUtils.toFloat(String.valueOf(obj), defaultValue);
        }
    }

    public static String getString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return String.valueOf(obj).trim();
        }
    }

    public static LocalDateTime getLocalDateTime(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof LocalDateTime) {
            return (LocalDateTime) obj;
        } else if (obj instanceof String) {
            return DateUtils.parse((String) obj);
        }
        return null;
    }
}

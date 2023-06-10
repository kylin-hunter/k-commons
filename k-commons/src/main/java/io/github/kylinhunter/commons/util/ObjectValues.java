package io.github.kylinhunter.commons.util;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.date.DateUtils;
import io.github.kylinhunter.commons.lang.BooleanUtil;
import io.github.kylinhunter.commons.lang.NumberUtil;
import io.github.kylinhunter.commons.reflect.ClassUtil;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author BiJi'an
 * @description get value from object
 * @date 2023/6/10
 */
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
    return BooleanUtil.toBoolean(obj.toString());
  }

  /**
   * @param obj obj
   * @return int
   * @title getInt
   * @description
   * @author BiJi'an
   * @date
   */
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
      return NumberUtil.toInt(String.valueOf(obj), defaultValue);
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
      return NumberUtil.toLong(String.valueOf(obj), defaultValue);
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
      return NumberUtil.toDouble(String.valueOf(obj), defaultValue);
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
      return NumberUtil.toFloat(String.valueOf(obj), defaultValue);
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

  @SuppressWarnings("unchecked")
  public static <T> List<T> getList(Object obj) {
    if (obj == null) {
      return Collections.emptyList();
    } else if (obj instanceof List) {
      return (List<T>) obj;
    } else if (obj instanceof Collection) {
      Collection<? extends T> c = (Collection<? extends T>) obj;
      List<T> list = ListUtils.newArrayList();
      list.addAll(c);
      return list;
    }
    return Collections.emptyList();
  }

  @SuppressWarnings("unchecked")
  public static <T> T get(Object obj, Class<T> clazz) {
    if (obj != null && clazz != null) {
      final Class<?> objClazz = obj.getClass();
      if (clazz.isAssignableFrom(objClazz)) {
        return (T) obj;
      } else {
        if (ClassUtil.isPrimitiveOrWrapper(clazz)) {
          if (clazz == int.class || clazz == Integer.class) {
            return (T) (Integer) NumberUtil.toInt(String.valueOf(obj));
          } else if (clazz == long.class || clazz == Long.class) {
            return (T) (Long) NumberUtil.toLong(String.valueOf(obj));
          } else if (clazz == float.class || clazz == Float.class) {
            return (T) (Float) NumberUtil.toFloat(String.valueOf(obj));
          } else if (clazz == double.class || clazz == Double.class) {
            return (T) (Double) NumberUtil.toDouble(String.valueOf(obj));
          } else if (clazz == short.class || clazz == Short.class) {
            return (T) (Short) NumberUtil.toShort(String.valueOf(obj));
          } else if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) (Boolean) BooleanUtil.toBoolean(String.valueOf(obj));
          }
        } else if (clazz == String.class) {
          return (T) String.valueOf(obj);
        }
      }
    }

    return null;
  }
}

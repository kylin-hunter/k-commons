package io.github.kylinhunter.commons.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.exception.embed.ParamException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description a enum util
 * @date 2022/1/1
 **/
@Slf4j
public class EnumUtils {
    private static final Map<Class<?>, Map<Integer, EnumCode>> ENUM_CODES = new HashMap<>();
    private static final Map<Class<?>, Map<String, EnumLabel>> ENUM_LABELS = new HashMap<>();

    /**
     * @param enumType enumType
     * @param label    label
     * @return T
     * @title get enum from code
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:19
     */
    public static <V extends EnumLabel> V fromLabel(Class<V> enumType, String label) {
        return fromLabel(enumType, label, true);
    }

    /**
     * @param enumType      enumType
     * @param label         label
     * @param throwIfFailed throwIfFailed
     * @return T
     * @title fromLabel
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:23
     */
    @SuppressWarnings("unchecked")
    public static <V extends EnumLabel> V fromLabel(Class<V> enumType, String label, boolean throwIfFailed) {
        V result = null;
        try {
            Map<String, EnumLabel> enumLabels = ENUM_LABELS.get(enumType);
            if (enumLabels == null) {
                synchronized(EnumUtils.class) {
                    if ((enumLabels = ENUM_LABELS.get(enumType)) == null) {
                        enumLabels = Maps.newHashMap();
                        ENUM_LABELS.put(enumType, enumLabels);
                        V[] enumConstants = enumType.getEnumConstants();
                        if (enumConstants != null && enumConstants.length > 0) {
                            for (V enumConstant : enumConstants) {
                                enumLabels.put(enumConstant.getLabel(), enumConstant);
                            }
                        }
                    }
                }
            }
            result = (V) enumLabels.get(label);
            if (result == null) {
                throw new ParamException("invalid enum label:" + label);
            }
        } catch (Exception e) {
            if (throwIfFailed) {
                throw new ParamException("invalid enum label:" + label, e);
            }
        }
        return result;

    }

    public static <V extends EnumLabel> V[] fromLabel(Class<V> enumType, String[] labels) {

        return fromLabel(enumType, labels, true);
    }

    /**
     * @param enumType enumType
     * @param labels   labels
     * @return T[]
     * @title fromLabel
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:30
     */
    @SuppressWarnings("unchecked")
    public static <V extends EnumLabel> V[] fromLabel(Class<V> enumType, String[] labels, boolean throwIfFailed) {
        if (labels != null && labels.length > 0) {
            V[] vs = (V[]) Array.newInstance(enumType, labels.length);
            for (int i = 0; i < labels.length; i++) {
                V v = fromLabel(enumType, labels[i], throwIfFailed);
                if (v == null) {
                    return null;
                }
                vs[i] = v;
            }
            return vs;
        }
        return null;

    }

    /**
     * @param enumType enumType
     * @param code     code
     * @return T
     * @title get enum from code
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:19
     */
    public static <V extends EnumCode> V fromCode(Class<V> enumType, int code) {
        return fromCode(enumType, code, true);
    }

    /**
     * @param enumType      enumType
     * @param code          code
     * @param throwIfFailed throwIfFailed
     * @return T
     * @title fromLabel
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:23
     */
    @SuppressWarnings("unchecked")
    public static <V extends EnumCode> V fromCode(Class<V> enumType, int code, boolean throwIfFailed) {
        V result = null;
        try {
            Map<Integer, EnumCode> enumCodes = ENUM_CODES.get(enumType);
            if (enumCodes == null) {
                synchronized(EnumUtils.class) {
                    if ((enumCodes = ENUM_CODES.get(enumType)) == null) {
                        enumCodes = Maps.newHashMap();
                        ENUM_CODES.put(enumType, enumCodes);
                        V[] enumConstants = enumType.getEnumConstants();
                        if (enumConstants != null && enumConstants.length > 0) {
                            for (V enumConstant : enumConstants) {
                                enumCodes.put(enumConstant.getCode(), enumConstant);
                            }
                        }
                    }
                }
            }
            result = (V) enumCodes.get(code);
            if (result == null) {
                throw new ParamException("invalid enum code:" + code);
            }

        } catch (Exception e) {
            if (throwIfFailed) {
                throw new ParamException("invalid enum code:" + code, e);
            }
        }

        return result;

    }

    public static <V extends EnumCode> V[] fromCode(Class<V> enumType, int[] codes) {

        return fromCode(enumType, codes, true);
    }

    /**
     * @param enumType enumType
     * @param codes    codes
     * @return T[]
     * @title fromLabel
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:30
     */
    @SuppressWarnings("unchecked")
    public static <V extends EnumCode> V[] fromCode(Class<V> enumType, int[] codes,
                                                    boolean throwIfFailed) {
        if (codes != null && codes.length > 0) {
            V[] vs = (V[]) Array.newInstance(enumType, codes.length);
            for (int i = 0; i < codes.length; i++) {
                V v = fromCode(enumType, codes[i], throwIfFailed);
                if (v == null) {
                    return null;
                }
                vs[i] = v;

            }
            return vs;
        }
        return null;

    }

    /**
     * @param enumType enumType
     * @param name     name1
     * @return T
     * @title get enum from code
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:19
     */
    public static <T extends Enum<T>> T fromName(Class<T> enumType, String name) {
        return fromName(enumType, name, true);
    }

    /**
     * @param enumType      enumType
     * @param name          name1
     * @param throwIfFailed throwIfFailed
     * @return T
     * @title fromLabel
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:23
     */
    public static <T extends Enum<T>> T fromName(Class<T> enumType, String name, boolean throwIfFailed) {
        try {
            return Enum.valueOf(enumType, name);
        } catch (Exception e) {
            log.error("fromName  error", e);
        }
        if (throwIfFailed) {
            throw new ParamException("invalid enum name:" + name);
        } else {
            return null;
        }

    }

    public static <T extends Enum<T>> T[] fromName(Class<T> enumType, String[] names) {
        return fromName(enumType, names, true);
    }

    /**
     * @param enumType enumType
     * @param names    names
     * @return T[]
     * @title fromLabel
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:30
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T[] fromName(Class<T> enumType, String[] names, boolean throwIfFailed) {
        if (names != null && names.length > 0) {

            T[] ts = (T[]) Array.newInstance(enumType, names.length);
            for (int i = 0; i < names.length; i++) {
                T t = fromName(enumType, names[i], throwIfFailed);
                if (t == null) {
                    return null;
                }

                ts[i] = t;
            }
            return ts;
        }
        return null;

    }

    public interface EnumCode {
        int getCode();
    }

    public interface EnumLabel {
        String getLabel();
    }

}

package com.kylinhunter.plat.commons.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import com.kylinhunter.plat.commons.exception.inner.ParamException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description a enum util
 * @date 2022/1/1
 **/
@Slf4j
public class EnumUtil {
    private static final Map<Class<?>, EnumCode[]> ENUM_DATAS = new HashMap<>();
    private static final Map<Class<?>, EnumLabel[]> ENUM_TAGSS = new HashMap<>();

    /**
     * @param enumType enumType
     * @param tag     tag
     * @return T
     * @title get enum from code
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:19
     */
    public static <T extends Enum<T>, V extends EnumLabel> T fromLabel(Class<V> enumType, String  tag) {
        return fromLabel(enumType, tag, true);
    }

    /**
     * @param enumType      enumType
     * @param tag          tag
     * @param throwIfFailed throwIfFailed
     * @return T
     * @title fromCode
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:23
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>, V extends EnumLabel> T fromLabel(Class<V> enumType, String  tag,
                                                                       boolean throwIfFailed) {
        try {
            EnumLabel[] enumLabels = ENUM_TAGSS.get(enumType);
            if (enumLabels == null) {
                enumLabels = enumType.getEnumConstants();
                if (enumLabels != null) {
                    ENUM_TAGSS.put(enumType, enumLabels);
                }
            }
            if (enumLabels != null) {
                for (EnumLabel enumlabel : enumLabels) {
                    if (enumlabel.getLabel().equals(tag)) {
                        return (T) enumlabel;
                    }
                }
            }

        } catch (Exception e) {

            log.error("fromCode  error", e);
        }
        if (throwIfFailed) {
            throw new ParamException("invalid enum tag:" + tag);
        } else {
            return null;
        }

    }

    public static <T extends Enum<T>, V extends EnumLabel> T[] fromLabel(Class<V> enumType, String[] tags) {

        return fromLabel(enumType, tags, true);
    }

    /**
     * @param enumType enumType
     * @param tags    tags
     * @return T[]
     * @title fromCode
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:30
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>, V extends EnumLabel> T[] fromLabel(Class<V> enumType, String[] tags,
                                                                         boolean throwIfFailed) {
        if (tags != null && tags.length > 0) {

            T[] ts = (T[]) Array.newInstance(enumType, tags.length);
            for (int i = 0; i < tags.length; i++) {
                T t = fromLabel(enumType, tags[i], throwIfFailed);
                if (t == null) {
                    return null;
                }
                ts[i] = t;
            }
            return ts;
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
    public static <T extends Enum<T>, V extends EnumCode> T fromCode(Class<V> enumType, int code) {
        return fromCode(enumType, code, true);
    }

    /**
     * @param enumType      enumType
     * @param code          code
     * @param throwIfFailed throwIfFailed
     * @return T
     * @title fromCode
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:23
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>, V extends EnumCode> T fromCode(Class<V> enumType, int code,
                                                                     boolean throwIfFailed) {
        try {
            EnumCode[] enumCodes = ENUM_DATAS.get(enumType);
            if (enumCodes == null) {
                enumCodes = enumType.getEnumConstants();
                if (enumCodes != null) {
                    ENUM_DATAS.put(enumType, enumCodes);
                }
            }
            if (enumCodes != null) {
                for (EnumCode enumCode : enumCodes) {
                    if (enumCode.getCode() == code) {
                        return (T) enumCode;
                    }
                }
            }

        } catch (Exception e) {

            log.error("fromCode  error", e);
        }
        if (throwIfFailed) {
            throw new ParamException("invalid enum code:" + code);
        } else {
            return null;
        }

    }

    public static <T extends Enum<T>, V extends EnumCode> T[] fromCode(Class<V> enumType, int[] codes) {

        return fromCode(enumType, codes, true);
    }

    /**
     * @param enumType enumType
     * @param codes    codes
     * @return T[]
     * @title fromCode
     * @description
     * @author BiJi'an
     * @date 2022/1/1 1:30
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>, V extends EnumCode> T[] fromCode(Class<V> enumType, int[] codes,
                                                                       boolean throwIfFailed) {
        if (codes != null && codes.length > 0) {

            T[] ts = (T[]) Array.newInstance(enumType, codes.length);
            for (int i = 0; i < codes.length; i++) {
                T t = fromCode(enumType, codes[i], throwIfFailed);
                if (t == null) {
                    return null;
                }
                ts[i] = t;
            }
            return ts;
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
     * @title fromCode
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
     * @title fromCode
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
                if(t==null){
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

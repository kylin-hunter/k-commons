package com.kylinhunter.plat.commons.exception.info;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.kylinhunter.plat.commons.exception.inner.InitException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description 错误代码管理
 * @date 2022/01/01
 **/

@Slf4j
public class ErrInfoManager {
    public static final Map<Integer, ErrInfo> ERR_INFOS = Maps.newLinkedHashMap();
    private static final ErrInfoManager singleton = new ErrInfoManager();

    public static String getDefaultMsg(int code) {
        return ERR_INFOS.getOrDefault(code, ErrInfos.UNKNOWN).getDefaultMsg();
    }

    static {
        singleton.init(ErrInfos.class);
    }

    private void register(ErrInfo errInfo) {
        if (ERR_INFOS.containsKey(errInfo.getCode())) {
            throw new InitException(" error code is used:" + errInfo.getCode());
        } else {
            ERR_INFOS.put(errInfo.getCode(), errInfo);
        }
    }

    public void init(Class<?> cls) {
        for (Field field : cls.getDeclaredFields()) {
            if (field.getType() == ErrInfo.class && Modifier.isFinal(field.getModifiers())) {
                try {
                    ErrInfo errInfo = (ErrInfo) field.get(null);
                    if (StringUtils.isEmpty(errInfo.getDefaultMsg())) {
                        errInfo.setDefaultMsg(field.getName());
                    }
                    register(errInfo);

                } catch (Exception e) {
                    throw new InitException("init ErrInfoManager error", e);
                }

            }
        }
    }

    public static void println() {
        log.info("print errInfo code ");
        ERR_INFOS.forEach((errCode, defaultMsg) -> log.info("erroCode={},defaultMsg={}", errCode, defaultMsg));

    }

}

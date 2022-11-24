package io.github.kylinhunter.commons.exception.info;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.sys.KConst;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description ErrInfo Manager
 * @date 2022/01/01
 **/

@Slf4j
public class ErrInfoManager {
    public static final Map<Integer, ErrInfo> ERR_INFOS = Maps.newLinkedHashMap();

    static {
        init(KConst.K_BASE_PACKAGE);
    }

    /**
     * @param pkgs pkgs
     * @return void
     * @title init
     * @description
     * @author BiJi'an
     * @date 2022-11-24 01:30
     */
    @SuppressWarnings("unchecked")
    public static void init(String... pkgs) {
        for (String pkg : pkgs) {
            Reflections reflections = new Reflections(pkg, Scanners.TypesAnnotated);
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ErrInfoAware.class);
            for (Class<?> clazz : classes) {
                Set<Field> allFields = ReflectionUtils.getAllFields(clazz);
                allFields.forEach(field -> {

                    int modifiers = field.getModifiers();
                    if (field.getType() == ErrInfo.class && Modifier.isStatic(modifiers)) {
                        try {
                            ErrInfo errInfo = (ErrInfo) field.get(null);
                            if (StringUtils.isEmpty(errInfo.getDefaultMsg())) {
                                errInfo.setDefaultMsg(field.getName());
                            }
                            if (ERR_INFOS.containsKey(errInfo.getCode())) {
                                throw new InitException(" error code is used:" + errInfo.getCode());
                            } else {
                                ERR_INFOS.put(errInfo.getCode(), errInfo);
                            }

                        } catch (Exception ex) {
                            throw new InitException("init ErrInfoManager error", ex);
                        }

                    }
                });
            }
        }

    }

    /**
     * @param code code
     * @return java.lang.String
     * @title getDefaultMsg
     * @description
     * @author BiJi'an
     * @date 2022-11-24 01:30
     */
    public static String getDefaultMsg(int code) {
        return ERR_INFOS.getOrDefault(code, ErrInfos.UNKNOWN).getDefaultMsg();
    }

    /**
     * @return void
     * @title println
     * @description
     * @author BiJi'an
     * @date 2022-11-24 01:30
     */
    public static void println() {
        log.info("print errInfo code ");
        ERR_INFOS.forEach((errCode, defaultMsg) -> log.info("erroCode={},defaultMsg={}", errCode, defaultMsg));

    }

    public static void main(String[] args) {
        ErrInfoManager.println();
    }

}

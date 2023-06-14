/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.exception.info;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.sys.KConst;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author BiJi'an
 * @description ErrInfo Manager
 * @date 2022/01/01
 */
public class ErrInfoManager {
  private static final Map<Integer, ErrInfo> ERR_INFOS = MapUtils.newLinkedHashMap();

  static {
    init(KConst.K_BASE_PACKAGE);
  }

  /**
   * @param pkgs pkgs
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
        allFields.forEach(
            field -> {
              int modifiers = field.getModifiers();
              if (field.getType() == ErrInfo.class && Modifier.isStatic(modifiers)) {
                try {
                  ErrInfo errInfo = (ErrInfo) field.get(null);
                  if (StringUtil.isEmpty(errInfo.getDefaultMsg())) {
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
   * @title println
   * @description
   * @author BiJi'an
   * @date 2022-11-24 01:30
   */
  public static void println() {
    System.out.println("print errInfo code\n ");
    ERR_INFOS.forEach(
        (errCode, defaultMsg) ->
            System.out.println("erroCode=" + errCode + ",defaultMsg=" + defaultMsg));
  }

  public static void main(String[] args) {
    ErrInfoManager.println();
  }
}

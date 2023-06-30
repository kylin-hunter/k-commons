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

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.init.AbstractInitializer;
import io.github.kylinhunter.commons.init.ClassScanner;
import io.github.kylinhunter.commons.init.Order;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.reflections.ReflectionUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-15 10:48
 */
@Order(Integer.MIN_VALUE)
public class ErrInfoInitializer extends AbstractInitializer {

  public ErrInfoInitializer(Set<String> pkgs) {
    super(pkgs);
  }

  public ErrInfoInitializer(ClassScanner classScanner) {
    super(classScanner);
  }

  /**
   * @title initialize
   * @description initialize
   * @author BiJi'an
   * @date 2023-06-19 17:33
   */
  @SuppressWarnings("unchecked")
  @Override
  public void init() throws InitException {

    Set<Class<?>> classes = this.classScanner.getTypesAnnotatedWith(ErrInfoAware.class);
    for (Class<?> clazz : classes) {
      Set<Field> allFields = ReflectionUtils.getAllFields(clazz);
      allFields.forEach(ErrInfoInitializer::processField);
    }
  }

  /**
   * @param field field
   * @title processField
   * @description processField
   * @author BiJi'an
   * @date 2023-06-21 01:22
   */
  private static void processField(Field field) {
    int modifiers = field.getModifiers();
    if (field.getType() == ErrInfo.class && Modifier.isStatic(modifiers)) {
      ErrInfo errInfo = (ErrInfo) ReflectUtils.get(null, field);
      if (StringUtil.isEmpty(errInfo.getDefaultMsg())) {
        errInfo.setDefaultMsg(field.getName());
      }
      ErrInfoManager.add(errInfo);
    }
  }
}

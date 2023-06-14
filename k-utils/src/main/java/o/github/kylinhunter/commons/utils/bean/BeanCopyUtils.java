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
package o.github.kylinhunter.commons.utils.bean;

import io.github.kylinhunter.commons.exception.embed.FormatException;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import o.github.kylinhunter.commons.utils.bean.copy.BeanCopy;
import o.github.kylinhunter.commons.utils.bean.copy.BeanCopyCache;
import o.github.kylinhunter.commons.utils.bean.info.BeanIntrospector;
import o.github.kylinhunter.commons.utils.bean.info.BeanIntrospectors;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
public class BeanCopyUtils {

  /**
   * @param source the source bean
   * @param target the target bean
   * @param ignoreProperties array of property names to ignore
   * @return void
   * @title copyProperties
   * @description Copy the property values of the given source bean into the given target bean
   * @author BiJi'an
   * @date 2021/8/13 8:52 下午
   */
  public static void copyProperties(Object source, Object target, String... ignoreProperties) {
    try {
      copy(source, target, ignoreProperties);
      BeanCopy beanCopy = BeanCopyCache.getBeanCopy(source.getClass(), target.getClass());
      if (beanCopy != null) {
        beanCopy.copy(source, target);
      }
    } catch (Exception e) {
      throw new FormatException("copyProperties error", e);
    }
  }

  /**
   * @param source source
   * @param target target
   * @param ignoreProperties ignoreProperties
   * @return void
   * @title copy
   * @description
   * @author BiJi'an
   * @date 2023-02-19 02:14
   */
  private static void copy(Object source, Object target, String... ignoreProperties) {

    BeanIntrospector sourceBeanIntrospector = BeanIntrospectors.get(source.getClass());
    Map<String, PropertyDescriptor> sourcePds = sourceBeanIntrospector.getPropertyDescriptors();

    BeanIntrospector targetBeanIntrospector = BeanIntrospectors.get(target.getClass());
    Map<String, PropertyDescriptor> targetPds = targetBeanIntrospector.getPropertyDescriptors();

    sourcePds.forEach(
        (name, pdSource) -> {
          if (!isIgnoreProperties(name, ignoreProperties)) {
            PropertyDescriptor pdTarget = targetPds.get(name);
            if (pdTarget != null) {
              Method readMethod = pdSource.getReadMethod();
              Method writeMethod = pdTarget.getWriteMethod();
              if (readMethod != null
                  && writeMethod != null
                  && writeMethod.getParameterCount() == 1
                  && writeMethod.getParameterTypes()[0].isAssignableFrom(
                      readMethod.getReturnType())) {
                Object readObj = ReflectUtils.invoke(source, readMethod);
                ReflectUtils.invoke(target, writeMethod, readObj);
              }
            }
          }
        });
  }

  /**
   * @param name name
   * @param ignoreProperties ignoreProperties
   * @return boolean
   * @title isIgnoreProperties
   * @description
   * @author BiJi'an
   * @date 2023-02-19 02:04
   */
  private static boolean isIgnoreProperties(String name, String... ignoreProperties) {
    if (ignoreProperties.length <= 0) {
      return false;
    }
    for (String ignoreProperty : ignoreProperties) {
      if (name.equals(ignoreProperty)) {
        return true;
      }
    }
    return false;
  }
}

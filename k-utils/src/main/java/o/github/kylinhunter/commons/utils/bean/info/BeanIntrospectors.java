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
package o.github.kylinhunter.commons.utils.bean.info;

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:15
 */
public class BeanIntrospectors {
  private static final Map<Class<?>, BeanIntrospector> beanIntrospectors = MapUtils.newHashMap();

  /**
   * @param clazz clazz
   * @return java.util.List<PropertyDescriptor>
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-02-19 01:21
   */
  public static BeanIntrospector get(Class<?> clazz) {
    BeanIntrospector beanIntrospector = beanIntrospectors.get(clazz);
    if (beanIntrospector == null) {
      synchronized (BeanIntrospectors.class) {
        beanIntrospector = beanIntrospectors.get(clazz);
        if (beanIntrospector == null) {
          beanIntrospector = new BeanIntrospector(clazz);
          beanIntrospectors.put(clazz, beanIntrospector);
        }
      }
    }
    return beanIntrospector;
  }
}

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
package io.github.kylinhunter.commons.component.simple;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 22:04
 */
public interface EnumService<T> {

  /**
   * @return java.lang.Class<? extends T>
   * @title getClazz
   * @description getClazz
   * @author BiJi'an
   * @date 2024-01-04 22:34
   */
  Class<? extends T> getSrvClazz();

  default Class[] getInitArgTypes() {
    return null;
  }

  /**
   * @return java.lang.Object[]
   * @title getInitargs
   * @description getInitargs
   * @author BiJi'an
   * @date 2024-01-05 00:17
   */
  default Object[] getInitArgs() {
    return null;
  }

}

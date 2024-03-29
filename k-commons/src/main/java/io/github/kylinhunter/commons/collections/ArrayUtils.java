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
package io.github.kylinhunter.commons.collections;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class ArrayUtils {

  /**
   * @param array array
   * @return boolean
   * @title isEmpty
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:42
   */
  public static boolean isEmpty(final Object[] array) {
    return array == null || array.length == 0;
  }

  /**
   * @param array array
   * @return boolean
   * @throws
   * @title isEmpty
   * @description isEmpty
   * @author BiJi'an
   * @date 2023-09-26 23:21
   */
  public static boolean isEmpty(final byte[] array) {
    return array == null || array.length == 0;
  }
}

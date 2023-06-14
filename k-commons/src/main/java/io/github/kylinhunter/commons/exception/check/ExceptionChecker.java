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
package io.github.kylinhunter.commons.exception.check;

import io.github.kylinhunter.commons.collections.ArrayUtils;
import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Collection;

/**
 * @author BiJi'an
 * @description
 * @date 2022/10/22
 */
public class ExceptionChecker {

  @SuppressWarnings("UnusedReturnValue")
  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new ParamException("can't be null ");
    }
    return reference;
  }

  /**
   * @param reference reference
   * @param errorMessage errorMessage
   * @return T
   * @title checkNotNull
   * @description
   * @author BiJi'an
   * @date 2022-10-22 22:00
   */
  @SuppressWarnings("UnusedReturnValue")
  public static <T> T checkNotNull(T reference, String errorMessage) {
    if (reference == null) {
      throw new ParamException(errorMessage);
    }
    return reference;
  }

  /**
   * @param collection collection
   * @param errorMessage errorMessage
   * @title checkNotEmpty
   * @description
   * @author BiJi'an
   * @date 2022-10-22 22:01
   */
  public static void checkNotEmpty(Collection<?> collection, String errorMessage) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new ParamException(errorMessage);
    }
  }

  /**
   * @param array array
   * @title checkNotEmpty
   * @description
   * @author BiJi'an
   * @date 2023-05-12 16:07
   */
  public static void checkNotEmpty(final Object[] array) {
    checkNotEmpty(array, "array can't be  empty");
  }

  /**
   * @param array array
   * @param errorMessage errorMessage
   * @title checkNotEmpty
   * @description
   * @author BiJi'an
   * @date 2023-05-12 16:07
   */
  public static void checkNotEmpty(final Object[] array, String errorMessage) {
    if (ArrayUtils.isEmpty(array)) {
      throw new ParamException(errorMessage);
    }
  }

  /**
   * @param name name
   * @param errorMessage errorMessage
   * @title checkNotEmpty
   * @description
   * @author BiJi'an
   * @date 2023-01-06 00:31
   */
  public static void checkNotEmpty(String name, String errorMessage) {
    if (StringUtil.isEmpty(name)) {
      throw new ParamException(errorMessage);
    }
  }

  /**
   * @param expression expression
   * @param errorMessage errorMessage
   * @title checkArgument
   * @description
   * @author BiJi'an
   * @date 2022-10-22 22:01
   */
  public static void checkArgument(boolean expression, String errorMessage) {
    if (!expression) {
      throw new ParamException(String.valueOf(errorMessage));
    }
  }

  /**
   * @param value value
   * @param name name
   * @title checkNonnegative
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:55
   */
  public static void checkNonnegative(int value, String name) {
    if (value < 0) {
      throw new ParamException(name + " cannot be negative but was: " + value);
    }
  }
}

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

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.ObjectCreator;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 22:03
 */
public class EnumServiceFactory {

  private static Object[] services;

  /**
   * @param service service
   * @return T
   * @title getService
   * @description getService
   * @author BiJi'an
   * @date 2024-01-04 22:34
   */
  @SuppressWarnings("unchecked")
  public <S> S getService(Enum<? extends EnumService<S>> service) {
    return (S) services[service.ordinal()];
  }

  /**
   * @param srvClazz srvClazz
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2024-01-04 22:35
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public <T extends Enum<? extends EnumService>> void init(Class<T> srvClazz) {

    T[] enumConstants = srvClazz.getEnumConstants();
    if (enumConstants != null) {
      services = new Object[enumConstants.length];
      for (T enumConstant : enumConstants) {
        if (enumConstant instanceof EnumService) {
          EnumService enumService = (EnumService) enumConstant;
          try {
            Object service = ObjectCreator.create(enumService.getClazz().getDeclaredConstructor());
            services[enumConstant.ordinal()] = service;
          } catch (NoSuchMethodException e) {
            throw new InitException("init error", e);
          }
        }
      }
    }
  }
}

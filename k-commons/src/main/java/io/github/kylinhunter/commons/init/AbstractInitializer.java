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
package io.github.kylinhunter.commons.init;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import java.util.Map;
import java.util.Set;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023/6/19
 */
@Slf4j
public abstract class AbstractInitializer implements Initializer {

  @Setter protected DebugOption debugOption;

  protected static volatile Map<Class<?>, Object> INITIALIZED_TAGS = MapUtils.newHashMap();

  @Setter protected ClassScanner classScanner;

  public AbstractInitializer(Set<String> pkgs) {
    this.classScanner = new ClassScanner(pkgs);
  }

  public AbstractInitializer(ClassScanner classScanner) {
    this.classScanner = classScanner;
  }

  /**
   * @title initialize
   * @description initialize
   * @author BiJi'an
   * @date 2023-06-21 00:57
   */
  public synchronized void initialize() throws InitException {
    Object initializedObj = INITIALIZED_TAGS.get(this.getClass());
    if (initializedObj == null) {
      INITIALIZED_TAGS.put(this.getClass(), this);
      log.info("Initializer = {} start ####", this.getClass().getName());
      init();
      log.info("Initializer = {} end ####", this.getClass().getName());
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T get(Class<T> clazz) {
    return (T) INITIALIZED_TAGS.get(clazz);
  }

  public abstract void init() throws InitException;
}

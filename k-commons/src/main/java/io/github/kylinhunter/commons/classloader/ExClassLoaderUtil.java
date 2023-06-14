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
package io.github.kylinhunter.commons.classloader;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.exception.embed.SystemException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-13 20:10
 */
public class ExClassLoaderUtil {

  /**
   * @param path path
   * @return void
   * @title addClassPath
   * @description
   * @author BiJi'an
   * @date 2022-11-19 02:10
   */
  public static void addClassPath(Path path) {

    try {

      ExClassLoader exClassLoader = ExClassLoader.getInstance();
      Method add = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      add.setAccessible(true);
      add.invoke(exClassLoader, path.toUri().toURL());
      ClassLoader parentClassLoader = exClassLoader.getParent();
      if (parentClassLoader instanceof URLClassLoader) {
        URLClassLoader classLoader = (URLClassLoader) parentClassLoader;
        add.invoke(classLoader, path.toUri().toURL());
      }
    } catch (Exception e) {
      throw new SystemException("addClassPath error", e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> Class<T> loadClass(String clazz) {
    try {
      return (Class<T>) ExClassLoader.getInstance().loadClass(clazz);
    } catch (Exception e) {
      throw new GeneralException("loadClass error", e);
    }
  }
}

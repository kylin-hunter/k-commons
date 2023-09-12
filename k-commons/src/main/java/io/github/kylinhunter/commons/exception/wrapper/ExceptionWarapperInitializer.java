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
package io.github.kylinhunter.commons.exception.wrapper;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.init.AbstractInitializer;
import io.github.kylinhunter.commons.init.ClassScanner;
import io.github.kylinhunter.commons.init.Order;
import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.sys.KGenerated;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator.ForClassLoader;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-15 10:48
 */
@Order(Integer.MIN_VALUE)
@KGenerated
public class ExceptionWarapperInitializer extends AbstractInitializer {

  private static final Logger log = Logger.getLogger(ExceptionWarapperInitializer.class.toString());

  private static final String CLASS_NAME_EXCEPTION_WRAPPER =
      "io.github.kylinhunter.commons.exception.wrapper.ExceptionWrapper";

  private final List<String> allClazzNames = ListUtils.newArrayList();

  public ExceptionWarapperInitializer(Set<String> pkgs) {
    super(pkgs);
  }

  public ExceptionWarapperInitializer(ClassScanner classScanner) {
    super(classScanner);
  }

  /**
   * @title initClassName
   * @description initClassName
   * @author BiJi'an
   * @date 2023-06-19 17:32
   */
  private void initClassName() {

    allClazzNames.add("io.github.kylinhunter.commons.classloader.ExClassLoaderUtil");
//  allClazzNames.add("io.github.kylinhunter.commons.exception.wrapper.ExceptionWrapperBean");
  }

  /**
   * @title initialize
   * @description initialize
   * @author BiJi'an
   * @date 2023-06-19 17:33
   */
  @Override
  public void init() throws InitException {

    try {
      initClassName();
      for (String clazzName : allClazzNames) {
        initialize(clazzName);
      }
    } catch (Throwable e) {
      log.warning("init error" + e.getMessage());
    }
  }

  /**
   * @param className className
   * @title initialize
   * @description initialize
   * @author BiJi'an
   * @date 2023-06-19 17:33
   */
  public void initialize(String className) {

    Loaded<?> loaded = null;
    try {
      TypePool typePool = TypePool.Default.ofSystemLoader();
      TypeDescription clazz = typePool.describe(className).resolve();
      TypeDescription ano = typePool.describe(CLASS_NAME_EXCEPTION_WRAPPER).resolve();
      loaded =
          new ByteBuddy()
              .rebase(clazz, ForClassLoader.ofSystemLoader())
              .method(ElementMatchers.isAnnotatedWith(ano))
              .intercept(MethodDelegation.to(ExceptionInvokeDelegation.class))
              .make()
              .load(ClassLoader.getSystemClassLoader(), Default.INJECTION);
      trySaveIn(loaded);
    } finally {
      IOUtil.closeQuietly(loaded);
    }
  }

  /**
   * @param loaded loaded
   * @title trySaveIn
   * @description trySaveIn
   * @author BiJi'an
   * @date 2023-06-19 23:40
   */
  private void trySaveIn(Loaded<?> loaded) {
    try {
      if (this.debugOption != null) {
        loaded.saveIn(debugOption.getClassSaveDir());
      }

    } catch (Throwable e) {
      log.warning("trySaveIn error:" + e.getMessage());
    }
  }
}

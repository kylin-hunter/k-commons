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
package io.github.kylinhunter.commons.component;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.init.ClassScanner;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 10:52
 */
@Slf4j
public class CompManager {

  protected final Map<Object, CObjects> allComponents = MapUtils.newHashMap();

  @Getter
  protected ClassScanner classScanner;
  protected final ConstructorCompManager constructorCompManager;
  protected final MethodCompManager methodCompManager;
  private final CFieldCompSetter cfieldCompSetter;
  private final CAfterMethodCalculator cafterMethodCalculator;

  public CompManager(ClassScanner classScanner) {
    this.classScanner = classScanner;
    constructorCompManager = new ConstructorCompManager(this);
    methodCompManager = new MethodCompManager(this);
    cfieldCompSetter = new CFieldCompSetter(this);
    cafterMethodCalculator = new CAfterMethodCalculator(this);
  }

  /**
   * @title init
   * @description
   * @author BiJi'an
   * @date 2023-02-12 22:47
   */
  public void init() {

    try {
      this.allComponents.clear();
      this.calComponent();
    } catch (Throwable e) {
      throw new InitException("init CompManager error", e);
    }
  }

  /**
   * @title calComponent
   * @description
   * @author BiJi'an
   * @date 2023-02-11 14:56
   */
  public void calComponent() {
    constructorCompManager.calculate();
    methodCompManager.calculate();
    cfieldCompSetter.calculate();
    cafterMethodCalculator.calculate();
  }

  /**
   * @param compClazz compClazz
   * @param required  required
   * @return java.util.List<T>
   * @title getComps
   * @description
   * @author BiJi'an
   * @date 2023-02-12 22:22
   */
  @SuppressWarnings("unchecked")
  public <T> List<T> getAll(Class<T> compClazz, boolean required) {
    Objects.requireNonNull(compClazz, "clazz can't be null");
    CObjects cobjects = allComponents.get(compClazz);
    if (cobjects != null && !cobjects.isEmpty()) {
      return (List<T>) cobjects.getObjects();
    }
    if (required) {
      throw new InitException("no component for :" + compClazz);
    }
    return null;
  }

  /**
   * @param key      key
   * @param required required
   * @return java.util.List<T>
   * @title getComp
   * @description
   * @author BiJi'an
   * @date 2023-02-12 22:22
   */
  @SuppressWarnings("unchecked")
  public <T, S extends T> T get(Object key, boolean required) {
    Objects.requireNonNull(key, "key can't be null");
    CObjects cobjects = allComponents.get(key);
    if (cobjects != null && !cobjects.isEmpty()) {
      return (T) cobjects.getObject();
    }
    if (required) {
      throw new InitException("no component for :" + key);
    }
    return null;
  }

  /**
   * @param clazz        clazz
   * @param cconstructor cconstructor
   * @param obj          obj
   * @return void
   * @title register
   * @description
   * @author BiJi'an
   * @date 2023-02-12 11:25
   */
  @SuppressWarnings("UnusedReturnValue")
  public List<CObjects> register(Class<?> clazz, CConstructor cconstructor, Object obj) {
    log.info("register {},{}", clazz.getName(), cconstructor.toString());
    CObject cobject = new CObject(cconstructor, obj);
    return register(clazz, cobject);
  }

  /**
   * @param clazz   clazz
   * @param cmethod cmethod
   * @param obj     obj
   * @return void
   * @title register
   * @description
   * @author BiJi'an
   * @date 2023-02-12 11:25
   */
  @SuppressWarnings("UnusedReturnValue")
  public List<CObjects> register(Class<?> clazz, CMethod cmethod, Object obj) {
    CObject cobject = new CObject(cmethod, obj);
    return register(clazz, cobject);
  }

  /**
   * @param clazz clazz
   * @param obj   obj
   * @return void
   * @title register
   * @description
   * @author BiJi'an
   * @date 2023-02-12 14:03
   */
  @SuppressWarnings("UnusedReturnValue")
  public List<CObjects> register(Class<?> clazz, Object obj) {
    CObject cobject = new CObject(true, 0, clazz.getSimpleName(), obj);
    return register(clazz, cobject);
  }

  /**
   * @param clazz clazz
   * @return void void
   * @title register
   * @description
   * @author BiJi'an
   * @date 2023-02-04 20:29
   */
  private List<CObjects> register(Class<?> clazz, CObject cobject) {
    try {

      List<CObjects> allAffectedCObjects = ListUtils.newArrayList();

      CObjects affectedCObject =
          allComponents.compute(
              clazz,
              (k, cobjects) -> {
                if (cobjects == null) {
                  cobjects = new CObjects();
                }
                cobjects.add(cobject);
                return cobjects;
              });
      register(clazz.getSimpleName(), cobject);

      String name = cobject.getName();
      if (!StringUtil.isEmpty(name) && !name.equals(clazz.getSimpleName())) {
        register(name, cobject);
      }

      allAffectedCObjects.add(affectedCObject);
      Set<Class<?>> allInterfaces = classScanner.getAllInterface(clazz);
      if (allInterfaces != null && allInterfaces.size() > 0) {
        for (Class<?> iterfaceClass : allInterfaces) {
          CObjects affectedCObjects =
              allComponents.compute(
                  iterfaceClass,
                  (k, cobjects) -> {
                    if (cobjects == null) {
                      cobjects = new CObjects();
                    }
                    cobjects.add(cobject);
                    return cobjects;
                  });
          allAffectedCObjects.add(affectedCObjects);
        }
      }
      return allAffectedCObjects;

    } catch (Exception e) {
      throw new InitException("init components error:" + clazz.getName(), e);
    }
  }

  /**
   * @param compClazzes compClazzes
   * @title check
   * @description
   * @author BiJi'an
   * @date 2023-02-12 22:57
   */
  public void check(Set<Class<?>> compClazzes) {

    for (Class<?> compClazz : compClazzes) {
      if (this.get(compClazz, false) == null) {
        throw new InitException("no    component be initialized " + compClazz.getName());
      }
    }
  }

  /**
   * @param name name
   * @param obj  obj
   * @title register
   * @description register
   * @author BiJi'an
   * @date 2023-11-25 18:06
   */
  public void register(String name, Object obj) {
    CObjects objects = new CObjects();
    objects.add(new CObject(name, obj));
    this.allComponents.put(name, objects);
  }

  /**
   * @param name    name
   * @param cobject cobject
   * @return void
   * @title register
   * @description register
   * @author BiJi'an
   * @date 2023-11-25 18:57
   */
  private void register(String name, CObject cobject) {
    if (!StringUtil.isEmpty(name)) {
      CObjects cObjects = this.allComponents.get(name);
      if (cObjects != null) {
        throw new InitException("invalid cobject name:" + cobject.getName());
      }
      CObjects objects = new CObjects();
      objects.add(cobject);
      this.allComponents.put(name, objects);
    }
  }
}

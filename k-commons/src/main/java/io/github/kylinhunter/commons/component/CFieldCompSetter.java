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

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.reflections.ReflectionUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 */
@RequiredArgsConstructor
class CFieldCompSetter {

  private CConstructorManager constructorManager;
  private CMethodManager methodManager;
  private CompManager compManager;

  public CFieldCompSetter(CompManager compManager) {
    this.compManager = compManager;
    this.constructorManager = compManager.constructorCompManager.constructorManager;
    this.methodManager = compManager.methodCompManager.methodManager;
  }

  /**
   * @return void
   * @title calComponents
   * @description
   * @author BiJi'an
   * @date 2023-01-20 00:27
   */
  public void calculate() {

    List<CField> allCFields = this.getAllCFields();

    for (CField cfield : allCFields) {
      Class<?> compClazz = cfield.getType();

      Field field = cfield.getField();
      Object compObject = cfield.getCompObject();
      Object value;
      if (List.class.isAssignableFrom(compClazz)) {
        Class<?> actualTypeArgumentClasses =
            GenericTypeUtils.getActualTypeArgument(cfield.getGenericType(), 0);
        value = compManager.getAll(actualTypeArgumentClasses, true);
        ReflectUtils.setField(compObject, field, value);
      } else {
        value = compManager.get(compClazz, false);
        if (value == null) {
          throw new InitException(
              compObject.getClass().getName() + " can't set " + compClazz.getName());
        }
      }
      ReflectUtils.setField(compObject, field, value);
    }
  }

  /**
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getAllCompClasses
   * @description
   * @author BiJi'an
   * @date 2023-02-12 22:42
   */
  @SuppressWarnings("unchecked")
  private List<CField> getAllCFields() {
    List<CField> allCFields = ListUtils.newArrayList();
    Set<Class<?>> allCompClasses = SetUtils.newHashSet(constructorManager.getCompClasses());
    allCompClasses.addAll(methodManager.getCompClasses());
    for (Class<?> compClass : allCompClasses) {
      Object o = compManager.get(compClass, true);
      Set<Field> fields =
          ReflectionUtils.getAllFields(compClass, (f) -> f.getAnnotation(CSet.class) != null);
      if (!CollectionUtils.isEmpty(fields)) {
        Set<CField> cFields =
            fields.stream().map(e -> new CField(e, o)).collect(Collectors.toSet());
        allCFields.addAll(cFields);
      }
    }
    return allCFields;
  }
}

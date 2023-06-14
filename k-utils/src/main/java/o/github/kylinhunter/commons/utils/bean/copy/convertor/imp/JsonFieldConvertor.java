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
package o.github.kylinhunter.commons.utils.bean.copy.convertor.imp;

import com.fasterxml.jackson.databind.JavaType;
import io.github.kylinhunter.commons.exception.embed.InitException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import o.github.kylinhunter.commons.utils.bean.copy.AbstractFieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.Direction;
import o.github.kylinhunter.commons.utils.json.JsonUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
public class JsonFieldConvertor extends AbstractFieldConvertor {

  private JavaType targetType;

  public JsonFieldConvertor(
      Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
    super(direction, sourcePD, targetPD);
    Class<?> propertyType =
        direction == Direction.FORWARD ? targetPD.getPropertyType() : sourcePD.getPropertyType();
    if (propertyType != String.class) {
      throw new InitException(" not a String type");
    }

    if (direction == Direction.BACKEND) {

      Method readMethod = targetPD.getReadMethod();
      if (List.class.isAssignableFrom(readMethod.getReturnType())) {
        Type targetReturnType = readMethod.getGenericReturnType();
        if (targetReturnType instanceof ParameterizedType) {
          ParameterizedType type = (ParameterizedType) targetReturnType;
          Class<?> clazz = (Class<?>) type.getActualTypeArguments()[0];
          targetType = JsonUtils.constructCollectionType(List.class, clazz);
        }
      }
    }
  }

  @Override
  public void forword(Object source, Object target) {
    Object sourceValue = this.read(source);
    String targetValue = JsonUtils.writeToString(sourceValue);
    this.write(target, targetValue);
  }

  @Override
  public void backward(Object source, Object target) {
    Object sourceValue = this.read(source);
    Object targetValue;
    if (targetType != null) {
      targetValue = JsonUtils.readValue(String.valueOf(sourceValue), targetType);
    } else {
      targetValue = JsonUtils.readToObject(String.valueOf(sourceValue), targetPD.getPropertyType());
    }
    this.write(target, targetValue);
  }
}

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
package io.github.kylinhunter.commons.utils.bean.copy.convertor.imp;

import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.lang.NumberUtil;
import io.github.kylinhunter.commons.utils.bean.copy.AbstractFieldConvertor;
import io.github.kylinhunter.commons.utils.bean.copy.convertor.Direction;
import java.beans.PropertyDescriptor;
import java.text.DecimalFormat;
import java.util.Set;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
public class NumberToStrFieldConvertor extends AbstractFieldConvertor {

  private static final ThreadLocal<DecimalFormat> decimalFormats =
      ThreadLocal.withInitial(() -> new DecimalFormat("#.######"));

  private static final Set<Class<?>> acceptedClazzes =
      SetUtils.newHashSet(
          int.class,
          Integer.class,
          long.class,
          Long.class,
          float.class,
          Float.class,
          double.class,
          Double.class);

  public NumberToStrFieldConvertor(
      Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
    super(direction, sourcePD, targetPD);
    Class<?> propertyType =
        direction == Direction.FORWARD ? targetPD.getPropertyType() : sourcePD.getPropertyType();
    if (propertyType != String.class) {
      throw new InitException("  require  a String type");
    }

    propertyType =
        direction == Direction.FORWARD ? sourcePD.getPropertyType() : targetPD.getPropertyType();
    if (!acceptedClazzes.contains(propertyType)) {
      throw new InitException("  no accept type=>" + propertyType.getName());
    }
  }

  @Override
  public void forword(Object source, Object target) {
    final Class<?> propertyType = sourcePD.getPropertyType();

    Object sourceValue = this.read(source);
    if (sourceValue != null) {
      if (propertyType == int.class
          || propertyType == Integer.class
          || propertyType == long.class
          || propertyType == Long.class) {
        this.write(target, String.valueOf(sourceValue));

      } else {
        this.write(target, decimalFormats.get().format(((Number) sourceValue).doubleValue()));
      }
    }
  }

  @Override
  public void backward(Object source, Object target) {
    final Class<?> propertyType = targetPD.getPropertyType();

    String sourceValue = this.read(source);
    if (sourceValue != null) {
      if (propertyType == int.class || propertyType == Integer.class) {
        this.write(target, NumberUtil.toInt(sourceValue));
      } else if (propertyType == long.class || propertyType == Long.class) {
        this.write(target, NumberUtil.toLong(sourceValue));
      } else if (propertyType == float.class || propertyType == Float.class) {
        this.write(target, NumberUtil.toFloat(sourceValue));
      } else if (propertyType == double.class || propertyType == Double.class) {
        this.write(target, NumberUtil.toDouble(sourceValue));
      }
    }
  }
}

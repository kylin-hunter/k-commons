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

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.serialize.ObjectBytesSerializer;
import java.beans.PropertyDescriptor;
import o.github.kylinhunter.commons.utils.bean.copy.AbstractFieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.ConvertExcetion;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.Direction;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
public class BytesFieldConvertor extends AbstractFieldConvertor {

  public BytesFieldConvertor(
      Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
    super(direction, sourcePD, targetPD);
    Class<?> propertyType =
        direction == Direction.FORWARD ? targetPD.getPropertyType() : sourcePD.getPropertyType();
    if (!propertyType.isArray() || !(propertyType.getComponentType() == byte.class)) {
      throw new InitException(" not a byte[] type");
    }
  }

  @Override
  public void forword(Object source, Object target) throws ConvertExcetion {
    try {
      Object sourceValue = this.read(source);
      if (sourceValue != null) {
        this.write(target, ObjectBytesSerializer.serialize(sourceValue));
      }
    } catch (Exception e) {
      throw new ConvertExcetion(" convert error ", e);
    }
  }

  @Override
  public void backward(Object source, Object target) throws ConvertExcetion {
    Object sourceValue = this.read(source);
    if (sourceValue != null) {
      try {
        this.write(target, ObjectBytesSerializer.deserialize((byte[]) sourceValue));
      } catch (Exception e) {
        throw new ConvertExcetion("convert error", e);
      }
    }
  }
}

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
package o.github.kylinhunter.commons.utils.bean.copy;

import io.github.kylinhunter.commons.exception.embed.InternalException;
import java.beans.PropertyDescriptor;
import lombok.AllArgsConstructor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.ConvertExcetion;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.Direction;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.FieldConvertor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
@AllArgsConstructor
public abstract class AbstractFieldConvertor implements FieldConvertor {

  private final Direction direction;
  protected final PropertyDescriptor sourcePD;
  protected final PropertyDescriptor targetPD;

  /**
   * @param source source
   * @return java.lang.Object
   * @title read
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:03
   */
  @SuppressWarnings("unchecked")
  public <T> T read(Object source) {
    try {
      return (T) sourcePD.getReadMethod().invoke(source);
    } catch (Exception e) {
      throw new InternalException("read error", e);
    }
  }

  /**
   * @param target target
   * @param value value
   * @return void
   * @title write
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:06
   */
  public void write(Object target, Object value) {
    try {
      targetPD.getWriteMethod().invoke(target, value);
    } catch (Exception e) {
      throw new InternalException("write error", e);
    }
  }

  @Override
  public void convert(Object source, Object target) throws ConvertExcetion {
    try {
      if (direction == Direction.FORWARD) {
        this.forword(source, target);
      } else {
        this.backward(source, target);
      }
    } catch (ConvertExcetion convertExcetion) {
      throw convertExcetion;
    } catch (Exception e) {
      throw new ConvertExcetion("convert error", e);
    }
  }

  /**
   * @param source source
   * @param target target
   * @return void
   * @title forword
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:14
   */
  public abstract void forword(Object source, Object target) throws ConvertExcetion;

  /**
   * @param source source
   * @param target target
   * @return void
   * @title backward
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:14
   */
  public abstract void backward(Object source, Object target) throws ConvertExcetion;
}

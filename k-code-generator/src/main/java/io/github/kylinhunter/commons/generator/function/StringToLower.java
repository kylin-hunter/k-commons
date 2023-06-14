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
package io.github.kylinhunter.commons.generator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 01:00
 */
public class StringToLower extends AbstractFunction {

  @Override
  public AviatorObject call(Map<String, Object> env, AviatorObject param1) {
    String name = FunctionUtils.getStringValue(param1, env);
    return new AviatorString(name.toLowerCase());
  }

  public String getName() {
    return "k.str_lower";
  }
}

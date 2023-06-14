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
package o.github.kylinhunter.commons.utils.bean.copy.convertor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.imp.BytesFieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.imp.JsonFieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.imp.NumberToStrFieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.imp.XmlFieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.imp.YamlFieldConvertor;

@RequiredArgsConstructor
public enum ConvertType {
  JSON(JsonFieldConvertor.class),
  BYTES(BytesFieldConvertor.class),
  XML(XmlFieldConvertor.class),
  YAML(YamlFieldConvertor.class),
  NUM_STR(NumberToStrFieldConvertor.class);
  @Getter private final Class<? extends FieldConvertor> clazz;
}

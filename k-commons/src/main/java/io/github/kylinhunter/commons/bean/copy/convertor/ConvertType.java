package io.github.kylinhunter.commons.bean.copy.convertor;

import io.github.kylinhunter.commons.bean.copy.convertor.imp.BytesFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.JsonFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.NumberToStrFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.XmlFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.YamlFieldConvertor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConvertType {
  JSON(JsonFieldConvertor.class),
  BYTES(BytesFieldConvertor.class),
  XML(XmlFieldConvertor.class),
  YAML(YamlFieldConvertor.class),
  NUM_STR(NumberToStrFieldConvertor.class);
  @Getter private final Class<? extends FieldConvertor> clazz;
}

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

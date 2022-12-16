package io.github.kylinhunter.commons.bean.copy.convertor;

import io.github.kylinhunter.commons.bean.copy.convertor.imp.BytesFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.JsonFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.NumberToStrFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.XmlFieldConvertor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConvertType {
    OBJ_2_JSON(JsonFieldConvertor.class),
    OBJ_2_BYTES(BytesFieldConvertor.class),
    OBJ_2_XML(XmlFieldConvertor.class),
    NUM_2_STR(NumberToStrFieldConvertor.class);
    @Getter
    private final Class<? extends FieldConvertor> clazz;

}

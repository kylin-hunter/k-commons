package io.github.kylinhunter.commons.bean.copy.convertor;

import io.github.kylinhunter.commons.bean.copy.convertor.imp.BytesFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.JsonFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.imp.XmlFieldConvertor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConvertType {
    JSON(JsonFieldConvertor.class), // to text
    BYTES(BytesFieldConvertor.class), // to byte array
    XML(XmlFieldConvertor.class); // to xml
    @Getter
    private final Class<? extends FieldConvertor> clazz;

}

package io.github.kylinhunter.commons.bean.copy.convertor;

import io.github.kylinhunter.commons.bean.copy.convertor.imp.JsonFieldConvertor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConvertType {
    JSON(JsonFieldConvertor.class); // to json
    @Getter
    private final Class<? extends FieldConvertor> clazz;

}

package com.kylinhunter.plat.commons.util.name;

import com.kylinhunter.plat.commons.service.EService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NCStrategy implements EService {
    CAMEL_TO_SNAKE(NCCamelToSnake.class),
    SNAKE_TO_CAMEL_UP_FIRST(NCSnakeToCamelUpperFirst.class),
    SNAKE_TO_CAMEL(NCSnakeToCamel.class);
    @Getter
    public Class<?> clazz;
}

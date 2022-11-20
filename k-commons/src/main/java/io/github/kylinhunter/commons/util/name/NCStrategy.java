package io.github.kylinhunter.commons.util.name;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NCStrategy {
    CAMEL_TO_SNAKE(NCCamelToSnake.class),
    SNAKE_TO_CAMEL_UP_FIRST(NCSnakeToCamelUpperFirst.class),
    SNAKE_TO_CAMEL(NCSnakeToCamel.class);
    @Getter
    public Class<?> clazz;
}

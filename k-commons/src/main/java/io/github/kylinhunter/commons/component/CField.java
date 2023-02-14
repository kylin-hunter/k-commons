package io.github.kylinhunter.commons.component;

import java.lang.reflect.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class CField {

    @EqualsAndHashCode.Include
    private final Field field;
    private final Object ccObject;
    private boolean primary;
    private int depLevel;
    private int order;

    public CField(Field field, Object ccObject) {
        this.field = field;
        this.ccObject = ccObject;

    }
}

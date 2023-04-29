package io.github.kylinhunter.commons.component;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
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
    private final Object compObject;

    public CField(Field field, Object compObject) {
        this.field = field;
        this.compObject = compObject;

    }

    /**
     * @return java.lang.reflect.Type
     * @title getGenericType
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:24
     */
    public Type getGenericType() {
        return this.field.getGenericType();
    }

    /**
     * @return java.lang.Class<?>
     * @title getType
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:24
     */
    public Class<?> getType() {
        return field.getType();
    }
}

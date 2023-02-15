package io.github.kylinhunter.commons.component;

import java.lang.reflect.Method;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class CAfterMethod {

    @EqualsAndHashCode.Include
    private final Method method;
    private final Object compObject;

    public CAfterMethod(Method method, Object compObject, CAfter cafter) {
        this.method = method;
        this.compObject = compObject;
    }
}

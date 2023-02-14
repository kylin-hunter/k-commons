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
class CMethod {

    @EqualsAndHashCode.Include
    private final Method method;
    private final Object ccObject;
    private boolean primary;
    private int depLevel;
    private int order;

    public CMethod(Method method, Object ccObject, C c) {
        this.method = method;
        this.ccObject = ccObject;
        this.primary = c.primary();
        this.order = c.order();

    }
}

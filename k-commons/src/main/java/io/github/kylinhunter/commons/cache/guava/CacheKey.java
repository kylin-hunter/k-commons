package io.github.kylinhunter.commons.cache.guava;

import com.google.common.base.Preconditions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 12:27
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CacheKey {
    @EqualsAndHashCode.Include
    private String key;
    private Object[] params;

    public CacheKey(Object... params) {
        Preconditions.checkArgument(params != null && params.length > 0, "param eror");
        this.params = params;
    }

}

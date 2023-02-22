package io.github.kylinhunter.commons.cache.guava;

import com.google.common.base.Preconditions;

import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.util.ObjectValues;
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

    public String getString(int index) {
        if (index < params.length) {
            return ObjectValues.getString(params[index]);
        } else {
            throw new ParamException("invalid index" + index);
        }

    }

    public int getInt(int index) {
        if (index < params.length) {
            return ObjectValues.getInt(params[index]);
        } else {
            throw new ParamException("invalid index" + index);
        }

    }

}

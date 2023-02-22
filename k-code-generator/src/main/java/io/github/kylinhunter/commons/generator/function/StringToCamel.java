package io.github.kylinhunter.commons.generator.function;

import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.name.CamelFormat;
import io.github.kylinhunter.commons.name.SnakeToCamel;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-17 01:00
 **/
public class StringToCamel extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject param1, AviatorObject param2) {
        SnakeToCamel snakeToCamel = CF.get(SnakeToCamel.class);
        String name = FunctionUtils.getStringValue(param1, env);
        String type = FunctionUtils.getStringValue(param2, env);
        if ("LOWER".equalsIgnoreCase(type)) {
            return new AviatorString(snakeToCamel.convert(name, CamelFormat.LOWER));
        } else {
            return new AviatorString(snakeToCamel.convert(name, CamelFormat.UPPER));
        }
    }

    public String getName() {
        return "k.string_to_camel";
    }
}
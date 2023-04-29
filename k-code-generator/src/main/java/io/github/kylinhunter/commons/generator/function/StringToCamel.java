package io.github.kylinhunter.commons.generator.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import io.github.kylinhunter.commons.name.CamelFormat;
import io.github.kylinhunter.commons.name.SnakeToCamelUtils;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 01:00
 **/
public class StringToCamel extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject param1, AviatorObject param2) {
        String name = FunctionUtils.getStringValue(param1, env);
        String type = FunctionUtils.getStringValue(param2, env);
        if ("LOWER".equalsIgnoreCase(type)) {
            return new AviatorString(SnakeToCamelUtils.convert(name, CamelFormat.LOWER));
        } else {
            return new AviatorString(SnakeToCamelUtils.convert(name, CamelFormat.UPPER));
        }
    }

    public String getName() {
        return "k.str_camel";
    }
}

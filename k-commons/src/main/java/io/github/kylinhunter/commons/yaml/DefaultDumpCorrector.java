package io.github.kylinhunter.commons.yaml;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.name.CamelToSnake;
import io.github.kylinhunter.commons.name.SnakeToCamel;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-08 15:20
 **/
@C
public class DefaultDumpCorrector extends DefaultLoadCorrector implements DumpCorrector {

    public DefaultDumpCorrector(SnakeToCamel snakeToCamel, CamelToSnake camelToSnake) {
        super(snakeToCamel, camelToSnake);
    }
}

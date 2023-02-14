package io.github.kylinhunter.commons.generator.context;

import io.github.kylinhunter.commons.generator.context.bean.CodeContext;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:22
 **/
public interface Handler {

    void handle(CodeContext codeContext);
}

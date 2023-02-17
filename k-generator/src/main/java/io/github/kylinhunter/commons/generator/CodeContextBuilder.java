package io.github.kylinhunter.commons.generator;

import java.util.List;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.context.bean.CodeContext;
import io.github.kylinhunter.commons.generator.context.handler.ContextHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 01:20
 **/
@C
public class CodeContextBuilder {
    @CSet
    private List<ContextHandler> contextHandlers;

    public CodeContext build() {
        CodeContext codeContext = new CodeContext();
        for (ContextHandler contextHandler : contextHandlers) {
            contextHandler.handle(codeContext);
        }
        return codeContext;
    }
}

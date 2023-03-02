package io.github.kylinhunter.commons.cmd;

import java.io.InputStream;
import java.util.concurrent.Callable;

import io.github.kylinhunter.commons.io.IOHelper;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 10:35
 **/
@RequiredArgsConstructor
public class CmdResultReader implements Callable<String> {

    private final Process process;
    private final ResultType type;

    @Override
    public String call() throws Exception {
        if (type == ResultType.STD_OUT) {
            try (InputStream stream = process.getInputStream()) {
                return IOHelper.toString(stream);
            }
        } else {
            try (InputStream stream = process.getErrorStream()) {
                return IOHelper.toString(stream);
            }
        }

    }

    enum ResultType {
        STD_OUT,  // the  standard ouput stream
        STD_ERR  // the standard err stream
    }
}

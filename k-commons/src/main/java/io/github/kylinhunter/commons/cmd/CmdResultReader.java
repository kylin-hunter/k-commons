package io.github.kylinhunter.commons.cmd;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;

import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 10:35
 **/
@RequiredArgsConstructor
public class CmdResultReader implements Callable<List<String>> {

    private final Process process;
    private final ResultType type;

    @Override
    public List<String> call() throws Exception {
        if (type == ResultType.STD_OUT) {
            try (InputStream stream = process.getInputStream()) {
                return IOUtils.readLines(stream, Charset.defaultCharset());
            }
        } else {
            try (InputStream stream = process.getErrorStream()) {
                return IOUtils.readLines(stream, Charset.defaultCharset());
            }
        }

    }

    enum ResultType {
        STD_OUT,  // the  standard ouput stream
        STD_ERR  // the standard err stream
    }
}

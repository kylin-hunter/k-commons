package io.github.kylinhunter.commons.cmd;

import java.util.List;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 02:03
 **/
@Data
public class CmdResult {
    private int exitValue;
    private List<String> stdOuts;
    private List<String> stdErrs;

    public boolean isSuccess() {
        return exitValue == 0;
    }
}

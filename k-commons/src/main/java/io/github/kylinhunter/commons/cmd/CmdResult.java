package io.github.kylinhunter.commons.cmd;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 02:03
 **/
@Data
public class CmdResult {
    private int exitValue;
    private String stdOut;
    private String stdErr;

    public boolean isSuccess() {
        return exitValue == 0;
    }
}

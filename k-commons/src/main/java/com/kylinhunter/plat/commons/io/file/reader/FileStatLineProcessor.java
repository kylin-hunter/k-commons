package com.kylinhunter.plat.commons.io.file.reader;

import com.kylinhunter.plat.commons.io.file.reader.FileStatLineProcessor.FileStat;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-18 01:31
 **/
public class FileStatLineProcessor extends DefaultLineProcessor<FileStat> {
    private final FileStat fileStat = new FileStat();

    @Override
    public void process(String line) {
        fileStat.addLineNum();
    }

    @Override
    public FileStat getResult() {
        return fileStat;
    }

    @Data
    public static class FileStat {
        private int lineNum;

        public void addLineNum() {
            this.lineNum++;
        }
    }

}

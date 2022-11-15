package com.kylinhunter.plat.commons.io.file.reader;

/**
 * @author BiJi'an
 * @description default line processor
 * @date 2022/1/1
 **/
public abstract class DefaultLineProcessor<T> implements LineProcessor {

    public abstract T getResult();
}

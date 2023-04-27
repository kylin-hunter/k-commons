package io.github.kylinhunter.commons.io.file.filter;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 16:18
 **/
public abstract class AbstractPathFilter implements PathFilter {
    @Override
    public FileVisitResult accept(Path file, BasicFileAttributes attributes) {
        return Files.isRegularFile(file) ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
    }

}

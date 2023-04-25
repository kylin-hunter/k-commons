package io.github.kylinhunter.commons.io.file.filter;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@FunctionalInterface
public interface PathFilter {

    FileVisitResult accept(Path path, BasicFileAttributes attributes);

    default PathFilter and(final PathFilter fileFilter) {
        return new AndPathFilter(this, fileFilter);
    }

    default PathFilter or(final PathFilter fileFilter) {
        return new OrPathFilter(this, fileFilter);
    }
}

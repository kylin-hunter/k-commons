package io.github.kylinhunter.commons.io.file.filter;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;

import io.github.kylinhunter.commons.collections.ListUtils;

public class OrPathFilter extends AbstractPathFilter {
    private final List<PathFilter> fileFilters;

    public OrPathFilter(List<PathFilter> fileFilters) {
        this.fileFilters = Objects.requireNonNull(fileFilters, "fileFilters");
    }

    public OrPathFilter(final PathFilter filter1, final PathFilter filter2) {
        this.fileFilters = ListUtils.newArrayListWithCapacity(2);
        addPathFilter(filter1);
        addPathFilter(filter2);
    }

    public void addPathFilter(final PathFilter... fileFilters) {
        for (final PathFilter fileFilter : Objects.requireNonNull(fileFilters, "fileFilters")) {
            addPathFilter(fileFilter);
        }
    }

    @Override
    public FileVisitResult accept(final Path file, final BasicFileAttributes attributes) {
        for (final PathFilter fileFilter : fileFilters) {
            if (fileFilter.accept(file, attributes) == FileVisitResult.CONTINUE) {
                return FileVisitResult.CONTINUE;
            }
        }
        return FileVisitResult.TERMINATE;
    }

}

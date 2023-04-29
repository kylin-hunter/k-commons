package io.github.kylinhunter.commons.io.file.filter;

import io.github.kylinhunter.commons.io.IOCase;
import io.github.kylinhunter.commons.strings.StringUtil;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 19:12
 **/
public class SuffixPathFilter extends AbstractPathFilter {
    private final String[] suffixes;
    private final IOCase ioCase;

    public SuffixPathFilter(final String... suffixes) {
        this(suffixes, IOCase.SENSITIVE);
    }

    public SuffixPathFilter(final String[] suffixes, final IOCase ioCase) {
        Objects.requireNonNull(suffixes, "suffixes must not be null");
        this.suffixes = new String[suffixes.length];
        System.arraycopy(suffixes, 0, this.suffixes, 0, suffixes.length);
        initSuffixes();
        this.ioCase = ioCase == null ? IOCase.SENSITIVE : ioCase;
    }

    public SuffixPathFilter(final List<String> suffixes) {
        this(suffixes, IOCase.SENSITIVE);
    }

    public SuffixPathFilter(final List<String> suffixes, final IOCase ioCase) {
        Objects.requireNonNull(suffixes, "suffixes must not be null");
        this.suffixes = suffixes.toArray(StringUtil.EMPTY_STRING_ARRAY);
        initSuffixes();
        this.ioCase = ioCase == null ? IOCase.SENSITIVE : ioCase;
    }

    /**
     * @return void
     * @title initSuffixes
     * @description
     * @author BiJi'an
     * @date 2023-04-22 20:49
     */
    private void initSuffixes() {
        for (int i = 0; i < suffixes.length; i++) {
            if (!this.suffixes[i].startsWith(".")) {
                this.suffixes[i] = "." + suffixes[i];
            }
        }
    }

    @Override
    public FileVisitResult accept(final Path file, final BasicFileAttributes attributes) {
        if (accept(Objects.toString(file.getFileName(), null))) {
            return FileVisitResult.CONTINUE;
        } else {
            return FileVisitResult.TERMINATE;
        }
    }

    /**
     * @param name name
     * @return boolean
     * @title accept
     * @description
     * @author BiJi'an
     * @date 2023-04-22 20:49
     */
    private boolean accept(final String name) {
        for (final String suffix : this.suffixes) {
            if (ioCase.checkEndsWith(name, suffix)) {
                return true;
            }
        }
        return false;
    }
}

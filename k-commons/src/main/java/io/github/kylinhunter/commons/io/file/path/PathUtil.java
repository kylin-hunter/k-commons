package io.github.kylinhunter.commons.io.file.path;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.file.filter.PathFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 14:39
 **/
public class PathUtil {
    @SuppressFBWarnings("MS_PKGPROTECT")
    public static final LinkOption[] NOFOLLOW_LINK_OPTION_ARRAY = {LinkOption.NOFOLLOW_LINKS};

    public static final LinkOption[] EMPTY_LINK_OPTION_ARRAY = {};

    /**
     * @param first first
     * @param more  more
     * @return java.nio.file.Path
     * @title get
     * @description
     * @author BiJi'an
     * @date 2023-04-22 23:16
     */
    public static Path get(String first, String... more) {
        return FileSystems.getDefault().getPath(first, more);
    }

    /**
     * @param start          start
     * @param pathFilter     pathFilter
     * @param maxDepth       maxDepth
     * @param readAttributes readAttributes
     * @param options        options
     * @return java.util.stream.Stream<java.nio.file.Path>
     * @title walk
     * @description
     * @author BiJi'an
     * @date 2023-04-22 15:25
     */
    public static Stream<Path> walk(final Path start, final PathFilter pathFilter, final int maxDepth,
                                    final boolean readAttributes, final FileVisitOption... options) {
        try {
            return Files.walk(start, maxDepth, options).filter(path -> pathFilter.accept(path,
                    readAttributes ? readBasicFileAttributes(path) : null) == FileVisitResult.CONTINUE);
        } catch (IOException e) {
            throw new KIOException("walk error", e);

        }
    }

    /**
     * @param path path
     * @return java.nio.file.attribute.BasicFileAttributes
     * @title readBasicFileAttributes
     * @description
     * @author BiJi'an
     * @date 2023-04-22 15:24
     */
    public static BasicFileAttributes readBasicFileAttributes(final Path path) {
        try {
            return Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            throw new KIOException("readBasicFileAttributes error", e);
        }
    }

    /**
     * @param path        path
     * @param linkOptions linkOptions
     * @return void
     * @title delete
     * @description
     * @author BiJi'an
     * @date 2023-04-22 16:01
     */
    public static void delete(final Path path, final LinkOption[] linkOptions) {
        if (Files.isDirectory(path, linkOptions)) {
            deleteDirectory(path, linkOptions);
        } else {
            deleteFile(path, linkOptions);
        }
    }

    /**
     * @param file        file
     * @param linkOptions linkOptions
     * @return boolean
     * @title deleteFile
     * @description
     * @author BiJi'an
     * @date 2023-04-22 16:01
     */
    public static boolean deleteFile(final Path file, final LinkOption[] linkOptions) {
        if (Files.isDirectory(file, linkOptions)) {
            throw new KIOException("invalid file" + file.toString());
        }
        try {
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new KIOException("delete file error", e);
        }
    }

    /**
     * @param directory   directory
     * @param linkOptions linkOptions
     * @return void
     * @title deleteDirectory
     * @description
     * @author BiJi'an
     * @date 2023-04-22 16:01
     */
    public static void deleteDirectory(final Path directory, final LinkOption[] linkOptions) {
        visitFileTree(new DeletingPathVisitor(linkOptions), directory);
    }

    /**
     * @param visitor   visitor
     * @param directory directory
     * @return T
     * @title visitFileTree
     * @description
     * @author BiJi'an
     * @date 2023-04-22 16:01
     */
    public static <T extends FileVisitor<? super Path>> T visitFileTree(final T visitor, final Path directory) {
        try {
            Files.walkFileTree(directory, visitor);
            return visitor;
        } catch (IOException e) {
            throw new KIOException("visitFileTree error", e);
        }

    }

    /**
     * @param directory directory
     * @return boolean
     * @title isEmptyDirectory
     * @description
     * @author BiJi'an
     * @date 2023-04-22 00:27
     */
    public static boolean isEmptyDirectory(final Path directory) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            return !directoryStream.iterator().hasNext();
        } catch (Exception e) {
            throw new KIOException("isEmptyDirectory error", e);
        }
    }
}

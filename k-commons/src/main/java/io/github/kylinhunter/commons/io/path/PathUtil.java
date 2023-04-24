package io.github.kylinhunter.commons.io.path;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import io.github.kylinhunter.commons.exception.embed.KIOException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 14:39
 **/
public class PathUtil {
    public static final LinkOption[] NOFOLLOW_LINK_OPTION_ARRAY = {LinkOption.NOFOLLOW_LINKS};

    public static final LinkOption[] EMPTY_LINK_OPTION_ARRAY = {};

    /**
     * @param path        path
     * @param linkOptions linkOptions
     * @return void
     * @title delete
     * @description
     * @author BiJi'an
     * @date 2023-04-22 16:01
     */
    public static void delete(final Path path, final LinkOption[] linkOptions) throws IOException {
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
    public static boolean deleteFile(final Path file, final LinkOption[] linkOptions) throws IOException {
        if (Files.isDirectory(file, linkOptions)) {
            throw new KIOException("invalid file" + file.toString());
        }
        return Files.deleteIfExists(file);
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
    public static void deleteDirectory(final Path directory, final LinkOption[] linkOptions) throws IOException {
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
    public static <T extends FileVisitor<? super Path>> T visitFileTree(final T visitor, final Path directory)
            throws IOException {
        Files.walkFileTree(directory, visitor);
        return visitor;
    }

    public static boolean isEmptyDirectory(final Path directory) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            return !directoryStream.iterator().hasNext();
        }
    }
}

package io.github.kylinhunter.commons.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaIOFileTest {
    private static String dir = "/Users/bijian/workspace_gitee/k-commons-test/java-io-test/";

    @Test
    void test() throws IOException {
        /*
        ln ./test1.txt ./test1_hard_link.txt
        ln -s  ./test1.txt ./test1_soft_link.txt
        ln -s ./test_dir1 ./test_dir1_soft_link
         */

        Path basePath = Paths.get(UserDirUtils.getDir("src/test/resources/test/java-io-test", false).getPath());
        if (Files.exists(basePath)) {

            print(basePath.resolve("test1.txt"), true, true, false, false);

            print(basePath.resolve("test1_hard_link.txt"), true, true, false, false);

            print(basePath.resolve("test1_soft_link.txt"), true, true, false, false);

            print(basePath.resolve("test_dir1"), true, true, true, true);

            print(basePath.resolve("test_dir1_soft_link"), true, true, true, false);
            System.out.println("### walkFileTree  start###");
            Files.walkFileTree(basePath, new TmpFilevisitor());
            System.out.println("### walkFileTree  end###");

            System.out.println("### walk  FOLLOW_LINK Sstart###");
            Stream<File> fileStream = Files.walk(basePath, FileVisitOption.FOLLOW_LINKS).map(Path::toFile);
            fileStream.forEach(file -> System.out.println(file.getAbsolutePath()));
            fileStream.close();
            System.out.println("### walk  FOLLOW_LINKS end###");

            System.out.println("### walk NO FOLLOW_LINK Sstart###");
            fileStream = Files.walk(basePath).map(Path::toFile);
            fileStream.forEach(file -> System.out.println(file.getAbsolutePath()));
            fileStream.close();
            System.out.println("### walk NO FOLLOW_LINKS end###");
        }

    }

    void print(Path path, boolean b1, boolean b2, boolean b3, boolean b4) {
        System.out.println("###" + path + "###");
        boolean exists1 = Files.exists(path);
        Assertions.assertEquals(b1, exists1);
        boolean exists2 = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
        Assertions.assertEquals(b2, exists2);
        System.out.println("exist:" + exists1 + ":" + exists1);

        boolean isDirectory1 = Files.isDirectory(path);
        boolean isDirectory2 = Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
        Assertions.assertEquals(b3, isDirectory1);
        Assertions.assertEquals(b4, isDirectory2);

        System.out.println(
                "isDirectory:" + isDirectory1 + ":" + isDirectory2);

    }

}

class TmpFilevisitor extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("preVisitDirectory" + dir);

        return super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("visitFile" + file);

        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("visitFileFailed" + file);

        return super.visitFileFailed(file, exc);
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("postVisitDirectory" + dir);
        return super.postVisitDirectory(dir, exc);
    }
}
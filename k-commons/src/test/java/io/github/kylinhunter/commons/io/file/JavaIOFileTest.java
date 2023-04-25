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

import org.junit.jupiter.api.Test;

class JavaIOFileTest {
    @Test
    void test() throws IOException {
        /*
        ln ./test1.txt ./test1_hard_link.txt
        ln -s  ./test1.txt ./test1_soft_link.txt
        ln -s ./test_dir1 ./test_dir1_soft_link
         */

        Path basePath = Paths.get("/Users/bijian/workspace_gitee/k-commons-test/tmp/");

        print(basePath.resolve("test1.txt"));

        print(basePath.resolve("test1_hard_link.txt"));

        print(basePath.resolve("test1_soft_link.txt"));

        print(basePath.resolve("test_dir1"));

        print(basePath.resolve("test_dir1_soft_link"));
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

    void print(Path path) {
        System.out.println("###" + path + "###");
        System.out.println("exist:" + Files.exists(path) + ":" + Files.exists(path, LinkOption.NOFOLLOW_LINKS));
        System.out.println(
                "isDirectory:" + Files.isDirectory(path) + ":" + Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS));

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
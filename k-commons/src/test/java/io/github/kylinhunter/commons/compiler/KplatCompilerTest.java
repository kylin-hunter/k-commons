package io.github.kylinhunter.commons.compiler;

import java.io.File;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.io.file.UserDirUtils;

class KplatCompilerTest {

    @Test
    public void compile() throws Exception {
        KplatCompiler kplatCompiler = new KplatCompiler();

        Set<String> path = SetUtils.newTreeSet();
        kplatCompiler.findClassPath("org", path);
        kplatCompiler.findClassPath("com", path);

        path.forEach(e -> {
            System.out.println("classpath=" + e);
        });

        File source = UserDirUtils.getFile("/src/main/java/io/github/kylinhunter/commons"
                + "/compiler/KplatCompiler.java");
        File compile = UserDirUtils.getTmpDir();

        kplatCompiler.addSource(source);
        kplatCompiler.setOutput(compile);

        kplatCompiler.compile();
    }

}
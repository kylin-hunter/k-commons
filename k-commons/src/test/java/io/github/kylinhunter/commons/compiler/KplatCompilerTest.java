package io.github.kylinhunter.commons.compiler;

import java.io.File;
import java.util.Set;

import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.io.file.UserDirUtils;

class KplatCompilerTest {

    public void compile() throws Exception {
        KplatCompiler kplatCompiler = new KplatCompiler();

        Set<String> path = Sets.newTreeSet();
        kplatCompiler.findClassPath("org", path);
        kplatCompiler.findClassPath("com", path);

        path.forEach(e -> {
            System.out.println("classpath=" + e);
        });

        File source = UserDirUtils.getFile("/src/main/java/com/kylinhunter/plat/commons"
                + "/compiler/KplatCompiler.java");
        File compile = UserDirUtils.getTmpDir();

        kplatCompiler.getSources().add(source);
        kplatCompiler.setOutput(compile);

        kplatCompiler.compile();
    }

}
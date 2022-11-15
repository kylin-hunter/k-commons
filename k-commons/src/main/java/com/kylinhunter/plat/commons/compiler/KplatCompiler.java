package com.kylinhunter.plat.commons.compiler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-14 00:28
 **/
@Slf4j
@Getter
@Setter
public class KplatCompiler {


    private List<File> sources = Lists.newArrayList();

    private File output;

    public void addSources(Collection<File> sourceFiles) {
        this.sources.addAll(sourceFiles);
    }

    public void compile() throws IOException {

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjects(sources.toArray(new File[0]));
        List<String> options = Arrays.asList("-d", output.getAbsolutePath());
        JavaCompiler.CompilationTask cTask = javaCompiler.getTask(null, fileManager, null, options, null, fileObjects);
        Boolean success = cTask.call();
        log.info("output result={}", success);
        fileManager.close();

    }

    public static void findClassPath(String prefix, Set<String> paths) throws IOException {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Enumeration<URL> resources = systemClassLoader.getResources(prefix);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            if (url.getProtocol().equals("jar")) {
                String file = url.getFile();
                int index = file.indexOf(".jar");
                paths.add(file.substring(0, index) + ".jar");
            } else {
                String file = url.getFile();
                paths.add(file.substring(0, file.length() - prefix.length() - 1));
            }
        }

    }
}

package io.github.kylinhunter.commons.compiler;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.exception.embed.KIOException;
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
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-14 00:28
 */
@Getter
@Setter
public class KplatCompiler {

  private List<File> sources = ListUtils.newArrayList();

  private File output;

  public void addSource(File file) {
    this.sources.add(file);
  }

  /**
   * @param sourceFiles sourceFiles
   * @return void
   * @title addSources
   * @description
   * @author BiJi'an
   * @date 2022-11-21 00:48
   */
  public void addSources(Collection<File> sourceFiles) {
    this.sources.addAll(sourceFiles);
  }

  /**
   * @return void
   * @title compile
   * @description
   * @author BiJi'an
   * @date 2022-11-21 00:48
   */
  public boolean compile() {
    JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

    try (StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null,
        null)) {

      Iterable<? extends JavaFileObject> fileObjects =
          fileManager.getJavaFileObjects(sources.toArray(new File[0]));
      List<String> options = Arrays.asList("-d", output.getAbsolutePath());
      JavaCompiler.CompilationTask cTask =
          javaCompiler.getTask(null, fileManager, null, options, null, fileObjects);
      return cTask.call();

    } catch (IOException e) {
      throw new KIOException("compile error", e);
    }
  }

  /**
   * @param prefix prefix
   * @param paths  paths
   * @return void
   * @title findClassPath
   * @description
   * @author BiJi'an
   * @date 2022-11-21 00:48
   */
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

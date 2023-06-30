package io.github.kylinhunter.commons.generator;

import io.github.kylinhunter.commons.classloader.ExClassLoaderUtil;
import io.github.kylinhunter.commons.compiler.KplatCompiler;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.File;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodeGeneratorTest {
  File sourceDir = ResourceHelper.getDir("$user.dir$/src_generator");

  File compileOutputDir = UserDirUtils.getTmpDir("src_generator/classes");

  @Test
  void execute() throws NoSuchFieldException {
    FileUtil.cleanDirectoryQuietly(compileOutputDir);

    CodeGenerator codeGenerator = new CodeGenerator();
    codeGenerator.execute();
    ExClassLoaderUtil.addClassPath(compileOutputDir.toPath());

    File file = new File(sourceDir, "com/kylinhunter/role/RoleBasic.java");
    check(file, "com.kylinhunter.role.RoleBasic");

    file = new File(sourceDir, "com/kylinhunter/role/RoleBasicSwagger.java");
    check(file, "com.kylinhunter.role.RoleBasicSwagger");

    file = new File(sourceDir, "com/kylinhunter/role/RoleBasicSwaggerSnippet.java");
    check(file, "com.kylinhunter.role.RoleBasicSwaggerSnippet");

    file = new File(sourceDir, "com/kylinhunter/user/UserBasic.java");
    check(file, "com.kylinhunter.user.UserBasic");

    file = new File(sourceDir, "com/kylinhunter/user/UserBasicSwagger.java");
    check(file, "com.kylinhunter.user.UserBasicSwagger");

    file = new File(sourceDir, "com/kylinhunter/user/UserBasicSwaggerSnippet.java");
    check(file, "com.kylinhunter.user.UserBasicSwaggerSnippet");
  }

  private void check(File file, String className) throws NoSuchFieldException {
    Assertions.assertTrue(file.exists());
    compile(file);
    Class<?> clazz = ExClassLoaderUtil.loadClass(className);
    System.out.println("loadClass=>" + clazz.getName());
    Assertions.assertEquals(
        clazz.getSuperclass().getName(), "io.github.kylinhunter.commons.io.file.FileUtil");
    Class<?>[] interfaces = clazz.getInterfaces();

    Assertions.assertEquals(interfaces[0].getName(), "java.io.Serializable");
    Assertions.assertEquals(interfaces[1].getName(), "java.lang.Cloneable");

    if (file.getName().toLowerCase().contains("swagger")) {
      Assertions.assertNotNull(clazz.getAnnotation(ApiModel.class));
      Assertions.assertNotNull(clazz.getDeclaredField("id").getAnnotation(ApiModelProperty.class));
    }
    if (file.getName().toLowerCase().contains("user")) {
      Assertions.assertEquals(Integer.class, clazz.getDeclaredField("roleId").getType());
      Assertions.assertEquals(LocalTime.class, clazz.getDeclaredField("birth").getType());
      Assertions.assertEquals(Short.class, clazz.getDeclaredField("age").getType());
      Assertions.assertEquals(Long.class, clazz.getDeclaredField("moneySpend").getType());
    }
  }

  /**
   * @param file file
   * @return void
   * @title compile
   * @description
   * @author BiJi'an
   * @date 2023-02-26 14:37
   */
  private void compile(File file) {
    KplatCompiler kplatCompiler = new KplatCompiler();
    kplatCompiler.addSource(file);
    kplatCompiler.setOutput(compileOutputDir);
    if (!kplatCompiler.compile()) {
      throw new KIOException("comipile failed");
    }
  }
}

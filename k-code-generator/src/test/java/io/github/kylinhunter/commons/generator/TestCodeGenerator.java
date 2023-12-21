package io.github.kylinhunter.commons.generator;

import io.github.kylinhunter.commons.classloader.ExClassLoaderUtil;
import io.github.kylinhunter.commons.compiler.KplatCompiler;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.execute.SqlReader;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.File;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;

class TestCodeGenerator {


  static void beforeAll() {

    DataSourceManager dataSourceManager = new DataSourceManager(true);
    DataSource dataSource = dataSourceManager.get();

    List<String> sqls = SqlReader.readSqls("io/github/kylinhunter/commons/generator/testdata.sql");

    SqlExecutor defaultSqlExecutor = dataSourceManager.getSqlExecutor();

    defaultSqlExecutor.executeBatch(true, sqls);

    DatabaseMetaReader databaseMetaReader = new DatabaseMetaReader(dataSource);
    ColumnMetas columnMetas = databaseMetaReader.getColumnReader()
        .getColumnMetaData(TestHelper.TABLE_ROLE);
    columnMetas.getNameColumns().forEach((k, v) -> {

      System.out.println(" table=>" + k);
      v.getRawMetadatas().forEach((k1, v1) -> System.out.println("  column=>" + k1 + "=>" + v1));
    });
    dataSourceManager.close();


  }


  static void execute() throws NoSuchFieldException {

    File sourceDir = UserDirUtils.getTmpDir("generator/src");
    File compileOutputDir = UserDirUtils.getTmpDir("generator/classes");

    FileUtil.cleanDirectoryQuietly(compileOutputDir);
    CodeGenerator codeGenerator = new CodeGenerator();
    codeGenerator.execute();
    verify(compileOutputDir, sourceDir);

  }

  public static void verify(File compileOutputDir, File sourceDir) throws NoSuchFieldException {
    ExClassLoaderUtil.addClassPath(compileOutputDir.toPath());

    File file = new File(sourceDir, "com/kylinhunter/role/RoleBasic.java");
    check(compileOutputDir, file, "com.kylinhunter.role.RoleBasic");

    file = new File(sourceDir, "com/kylinhunter/role/RoleBasicSwagger.java");
    check(compileOutputDir, file, "com.kylinhunter.role.RoleBasicSwagger");

    file = new File(sourceDir, "com/kylinhunter/role/RoleBasicSwaggerSnippet.java");
    check(compileOutputDir, file, "com.kylinhunter.role.RoleBasicSwaggerSnippet");

    file = new File(sourceDir, "com/kylinhunter/user/UserBasic.java");
    check(compileOutputDir, file, "com.kylinhunter.user.UserBasic");

    file = new File(sourceDir, "com/kylinhunter/user/UserBasicSwagger.java");
    check(compileOutputDir, file, "com.kylinhunter.user.UserBasicSwagger");

    file = new File(sourceDir, "com/kylinhunter/user/UserBasicSwaggerSnippet.java");
    check(compileOutputDir, file, "com.kylinhunter.user.UserBasicSwaggerSnippet");
  }

  public static void check(File compileOutputDir, File file, String className)
      throws NoSuchFieldException {

    Assertions.assertTrue(file.exists());
    compile(compileOutputDir, file);
    Class<?> clazz = ExClassLoaderUtil.loadClass(className);
    System.out.println("loadClass=>" + clazz.getName());

    Assertions.assertEquals(clazz.getSuperclass().getName(),
        "io.github.kylinhunter.commons.io.file.FileUtil");
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
    checkFileContent(file, clazz);
  }

  /**
   * @param file file
   * @title compile
   * @description
   * @author BiJi'an
   * @date 2023-02-26 14:37
   */
  private static void compile(File compileOutputDir, File file) {
    KplatCompiler kplatCompiler = new KplatCompiler();
    kplatCompiler.addSource(file);
    kplatCompiler.setOutput(compileOutputDir);
    if (!kplatCompiler.compile()) {
      throw new KIOException("comipile failed");
    }
  }

  /**
   * @param file  file
   * @param clazz clazz
   * @title checkFileContext
   * @description checkFileContext
   * @author BiJi'an
   * @date 2023-12-17 17:03
   */
  private static void checkFileContent(File file, Class<?> clazz) {
    File dir = UserDirUtils.getDir("src/test/verify/src_generator_standard");
    File distFile = new File(dir + "/" + clazz.getPackage().getName().replace('.', '/'),
        file.getName());
    if (!distFile.exists()) {
      dir = UserDirUtils.getDir("k-code-generator/src/test/verify/src_generator_standard");
      distFile = new File(dir + "/" + clazz.getPackage().getName().replace('.', '/'),
          file.getName());
    }
    Assertions.assertTrue(distFile.exists());
    String text1 = ResourceHelper.getText(file.getAbsolutePath());
    String text2 = ResourceHelper.getText(distFile.getAbsolutePath());
    Assertions.assertEquals(text1, text2);
  }


  public static void main(String[] args) throws NoSuchFieldException {
    beforeAll();
    execute();
  }
}

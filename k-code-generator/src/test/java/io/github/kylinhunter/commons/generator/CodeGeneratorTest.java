package io.github.kylinhunter.commons.generator;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.classloader.ExClassLoaderUtil;
import io.github.kylinhunter.commons.compiler.KplatCompiler;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

class CodeGeneratorTest {
    File sourceDir = ResourceHelper.getDir("$user.dir$/src_generator");

    File compileOutputDir = UserDirUtils.getTmpDir("src_generator/classes");

    @Test
    void execute() throws IOException, NoSuchFieldException {

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

    private void check(File file, String className) throws IOException, NoSuchFieldException {
        Assertions.assertTrue(file.exists());
        compile(file);
        Class<?> clazz = ExClassLoaderUtil.loadClass(className);
        System.out.println("loadClass=>" + clazz.getName());
        Assertions.assertEquals(clazz.getSuperclass().getName(), "java.util.ArrayList");
        Class<?>[] interfaces = clazz.getInterfaces();

        Assertions.assertEquals(interfaces[0].getName(), "java.io.Serializable");
        Assertions.assertEquals(interfaces[1].getName(), "java.lang.Cloneable");

        if (file.getName().toLowerCase().contains("swagger")) {
            Assertions.assertNotNull(clazz.getAnnotation(ApiModel.class));
            Assertions.assertNotNull(clazz.getDeclaredField("id").getAnnotation(ApiModelProperty.class));

        }

    }

    /**
     * @param file file
     * @return void
     * @title compile
     * @description
     * @author BiJi'an
     * @date 2023-02-23 14:37
     */
    private void compile(File file) throws IOException {
        KplatCompiler kplatCompiler = new KplatCompiler();
        kplatCompiler.addSource(file);
        kplatCompiler.setOutput(compileOutputDir);
        kplatCompiler.compile();
    }
}
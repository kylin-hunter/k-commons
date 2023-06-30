package io.github.kylinhunter.commons.clazz.test;

import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.io.File;
import java.io.IOException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-26 11:04
 */
public class Test {

  public static void main(String[] args) throws IOException {

    File tmpDir = UserDirUtils.getTmpDir("bytebuddy-test");
    DynamicType.Unloaded<?> dynamicType =
        new ByteBuddy()
            .with(
                new NamingStrategy.AbstractBase() {
                  @Override
                  protected String name(TypeDescription superClass) {
                    return "i.love.ByteBuddy." + superClass.getSimpleName();
                  }
                })
            .subclass(Object.class)
            .make();

    dynamicType.saveIn(tmpDir);
  }
}

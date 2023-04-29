package io.github.kylinhunter.commons.classloader;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import io.github.kylinhunter.commons.util.OnceRunner;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExClassLoaderUtilTest {

  @Test
  void loadClass() {
    OnceRunner.run(
        ExClassLoaderUtilTest.class,
        () -> {
          Assertions.assertThrows(
              GeneralException.class,
              () -> ExClassLoaderUtil.loadClass("io.github.kylinhunter.commons.Test1"));

          File ext = UserDirUtils.getDir("ext");
          ExClassLoaderUtil.addClassPath(ext.toPath());
          Class<Object> objectClass =
              ExClassLoaderUtil.loadClass("io.github.kylinhunter.commons.Test");
          Object o = ObjectCreator.create(objectClass);
          Assertions.assertNotNull(o);
        });
  }
}

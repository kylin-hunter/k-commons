package io.github.kylinhunter.commons.clazz.agent.plugin;

import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PluginManagerTest {


  @Test
  void init() {
    Instrumentation instrumentation = Mockito.mock(Instrumentation.class);
    File file = UserDirUtils.getFile("src/test/resources/k-agent-plugin-test.properties");
    System.out.println("file" + file.getAbsolutePath());
    Assertions.assertTrue(Files.exists(file.toPath()));
    String arg = "config-file=" + file.getAbsolutePath();
    PluginManager.initialize(arg, instrumentation);

  }

}
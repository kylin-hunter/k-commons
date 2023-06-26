package io.github.kylinhunter.commons.agent.invoke;

import io.github.kylinhunter.commons.clazz.agent.plugin.PluginManager;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvokeAnalysisPluginTest {


  @Mocked
  Instrumentation instrumentation;

  @Test
  void init() {
    File file = UserDirUtils.getFile(
        "src/test/resources/k-agent-plugin-invoke-analysis.properties");
    System.out.println("file" + file.getAbsolutePath());
    Assertions.assertTrue(Files.exists(file.toPath()));
    String arg = "config-file=" + file.getAbsolutePath();
    PluginManager.initialize(arg, instrumentation);

  }
}
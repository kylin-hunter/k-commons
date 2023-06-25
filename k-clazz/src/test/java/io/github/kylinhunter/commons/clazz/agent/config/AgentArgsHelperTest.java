package io.github.kylinhunter.commons.clazz.agent.config;

import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AgentArgsHelperTest {

  @Test
  void init() {
    File file = UserDirUtils.getFile("src/test/resources/k-agent-plugin-config.properties");
    System.out.println("file" + file.getAbsolutePath());
    Assertions.assertTrue(Files.exists(file.toPath()));

    String arg = "config-file=" + file.getAbsolutePath();

    AgentArgsHelper.init(arg);
    String configFilePath = AgentArgsHelper.getConfigFilePath();
    System.out.println("configFilePath=>" + configFilePath);
    Assertions.assertEquals(file.getAbsolutePath(), configFilePath);
  }
}
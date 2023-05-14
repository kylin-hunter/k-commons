package io.github.kylinhunter.commons.log.jul;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.io.ResourceHelper;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 16:41
 */
public class JULManager {

  private static String CONFIG_FILE = "k-jul-logging.properties";

  /**
   * @return
   * @title init
   * @description
   * @author BiJi'an
   * @date 2023-04-22 16:41
   */
  public static boolean init() {
    try {
      InputStream inputStream = ResourceHelper.getInputStreamInClassPath(CONFIG_FILE);
      if (inputStream != null) {
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(inputStream);
        return true;
      } else {
        System.err.println("no config file  be found :" + CONFIG_FILE);
        return false;
      }

    } catch (IOException e) {
      throw new GeneralException(" jul init error", e);
    }
  }

  /**
   * @param configFile configFile
   * @return void
   * @title reset
   * @description
   * @author BiJi'an
   * @date 2023-05-15 01:15
   */
  public static void setConfigFile(String configFile) {
    CONFIG_FILE = configFile;
  }
}

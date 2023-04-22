package io.github.kylinhunter.commons.log.jul;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.io.ResourceHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 16:41
 **/
public class JULManager {
    private static final String CONFIG_FILE = "k-jul-logging.properties";

    /**
     * @return
     * @title init
     * @description
     * @author BiJi'an
     * @date 2023-04-22 16:41
     */
    public static void init() {
        try {
            InputStream inputStream = ResourceHelper.getInputStreamInClassPath(CONFIG_FILE);
            if (inputStream != null) {
                LogManager logManager = LogManager.getLogManager();
                logManager.readConfiguration(inputStream);
            } else {
                System.err.println("no config file  be found :" + CONFIG_FILE);
            }

        } catch (IOException e) {
            throw new GeneralException(" jul init error", e);
        }
    }
}

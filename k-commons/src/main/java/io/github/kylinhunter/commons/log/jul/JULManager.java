package io.github.kylinhunter.commons.log.jul;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.LogManager;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.io.ResourceHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-20 16:41
 **/
public class JULManager {

    /**
     * @return
     * @title init
     * @description
     * @author BiJi'an
     * @date 2023-04-20 16:41
     */
    public static void init() {
        try {
            URL resource = ResourceHelper.class.getResource("/logging.properties");
            InputStream inputStream = ResourceHelper.getInputStreamInClassPath("logging.properties");
            System.out.println("resource"+resource);
            System.out.println("inputStream111"+inputStream);
            LogManager logManager = LogManager.getLogManager();

            logManager.readConfiguration(inputStream);
        } catch (IOException e) {
            throw new GeneralException(" jul init error", e);
        }
    }
}

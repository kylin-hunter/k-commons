package io.github.kylinhunter.commons.agent.invoke.test;

import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.github.kylinhunter.commons.clazz.agent.plugin.PluginManager;
import io.github.kylinhunter.commons.util.ThreadHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 14:50
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Student implements HomeWork {
    private static Logger log = Logger.getLogger(PluginManager.class.toString());

    @EqualsAndHashCode.Include
    public final String name;

    private Random random = new Random();



    @Override
    public void doHomeWork() {

        if (random.nextBoolean()) {
            this.fastDoing();
        } else {
            this.slowDoing();
        }

    }

    private void slowDoing() {
        log.info("I'm " + name + ", I'm  donging homework slowly,with 100 milliseconds");
        String         path = "org/apache/commons/io/FileUtils.class";

        URL resource = Student.class.getClassLoader().getResource(path);
        ThreadHelper.sleep(100, TimeUnit.MILLISECONDS);

    }

    private void fastDoing() {
        log.info("I'm " + name + ", I'm  donging homework fast,with 10 milliseconds");
        ThreadHelper.sleep(10, TimeUnit.MILLISECONDS);

    }


}

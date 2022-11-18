package io.github.kylinhunter.commons.classloader;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.exception.inner.GeneralException;
import io.github.kylinhunter.commons.io.file.UserDirUtils;

class ExClassLoaderUtilTest {

    @Test
    void loadClass() throws IllegalAccessException, InstantiationException {

        Assertions.assertThrows(GeneralException.class,
                () -> ExClassLoaderUtil.loadClass("io.github.kylinhunter.commons.Test"));

        File ext = UserDirUtils.getDir("ext");
        ExClassLoaderUtil.addClassPath(ext.toPath());
        Class<Object> objectClass = ExClassLoaderUtil.loadClass("io.github.kylinhunter.commons.Test");
        objectClass.newInstance();
    }
}
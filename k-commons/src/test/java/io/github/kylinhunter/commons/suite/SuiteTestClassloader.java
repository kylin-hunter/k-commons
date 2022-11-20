package io.github.kylinhunter.commons.suite;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-13 22:41
 **/
@Suite
@SuiteDisplayName("SuiteTestBean")
@SelectPackages("io.github.kylinhunter.commons.classloader")
public class SuiteTestClassloader {
}

package io.github.kylinhunter.commons.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class I18nUtilsTest {

  @Test
  void getCN() {
    I18nUtils.init("zh", "CN");
    Assertions.assertEquals("", I18nUtils.get(null));
    Assertions.assertEquals("", I18nUtils.get(""));
    Assertions.assertEquals("毕继安", I18nUtils.get("k.author"));
    Assertions.assertEquals("a commons tool-default", I18nUtils.get("k.description"));
    Assertions.assertEquals("今天是我的第100个幸运日,我想说：hello the world",
        I18nUtils.get("k.comment", new Object[]{
            100, "hello the world"
        }));

  }

  @Test
  void getEN() {

    I18nUtils.init("en", "US");

    Assertions.assertEquals("kylin hunter", I18nUtils.get("k.author"));
    Assertions.assertEquals("a commons tool", I18nUtils.get("k.description"));
    Assertions.assertEquals("today is my 100th lucky day, I want to say：hello the world",
        I18nUtils.get("k.comment", new Object[]{
            100, "hello the world"
        }));

  }

}
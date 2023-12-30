package io.github.kylinhunter.commons.tools;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.kylinhunter.commons.log.jul.JULManager;
import io.github.kylinhunter.commons.tools.WeakPasswordChecker.WeakPassOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WeakPasswordCheckerTest {

  @BeforeAll
  static void beforeAll() {
    JULManager.init();
  }

  @Test
  void check() {

    WeakPassOption weakPassOption = WeakPassOption.builder().build();
    WeakPasswordChecker weakPasswordChecker = new WeakPasswordChecker(weakPassOption);
    assertTrue(weakPasswordChecker.check(""));//空不行
    assertTrue(weakPasswordChecker.check("1"));//          有数字但是长度不够，没有小写字母、没有大写字母，没有特殊字符

    assertTrue(weakPasswordChecker.check("13579246"));//   有数字长度够了，没有小写字母、没有大写字母，没有特殊字符
    assertTrue(weakPasswordChecker.check("a13579246"));//  有数字长度够了，有小写字母，没有大写字母，没有特殊字符
    assertTrue(weakPasswordChecker.check("a13579246A"));// 有数字长度够了，有小写字母，有大写字母 、没有特殊字符
    assertFalse(weakPasswordChecker.check("a13579246A#"));// 有数字长度够了，有小写字母，有大写字母 、有特殊字符

    assertTrue(
        weakPasswordChecker.check("a13579246A#bijianlxp"));// 有数字长度够了，有小写字母，有大写字母 、有特殊字符，但是超长了

    assertFalse(weakPasswordChecker.check("a13579246A#aa"));// 有连续相同的3个字符，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#aaa"));// 有连续相同的3个字符，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#aaaa"));// 有连续相同的3个字符，是弱密码

    assertFalse(weakPasswordChecker.check("a13579246A#11"));// 有连续相同的3个数字，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#111"));// 有连续相同的3个数字，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#1111"));// 有连续相同的3个数字，是弱密码

    assertFalse(weakPasswordChecker.check("a13579246A#12"));// 有连续升序的3个数字，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#123"));// 有连续升序的3个数字，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#1234"));// 有连续升序的3个数字，是弱密码

    assertFalse(weakPasswordChecker.check("a13579246A#21"));// 有连续降序的3个数字，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#321"));// 有连续降序的3个数字，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#3210"));// 有连续降序的3个数字，是弱密码

    assertFalse(weakPasswordChecker.check("a13579246A#ab"));// 有连续升序的3个字母，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#abc"));// 有连续升序的3个字母，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#abcd"));// 有连续升序的3个字母，是弱密码

    assertFalse(weakPasswordChecker.check("a13579246A#dc"));// 有连续降序的3个字母，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#dcb"));// 有连续降序的3个字母，是弱密码
    assertTrue(weakPasswordChecker.check("a13579246A#dcba"));// 有连续降序的3个字母，是弱密码


  }

  @Test
  void checkOther() {
    WeakPassOption weakPassOptionStd = WeakPassOption.builder().build();
    WeakPasswordChecker weakPasswordCheckerStd = new WeakPasswordChecker(weakPassOptionStd);
    assertFalse(weakPasswordCheckerStd.check("a13579246A#"));//

    WeakPassOption weakPassOption = WeakPassOption.builder().containSpecialUppercaseLetter(false)
        .build();
    WeakPasswordChecker weakPasswordChecker = new WeakPasswordChecker(weakPassOption);
    String pwd = "a13579246#";
    assertTrue(weakPasswordCheckerStd.check(pwd));
    assertFalse(weakPasswordChecker.check(pwd));

    weakPassOption = WeakPassOption.builder().containSpecialNumbers(false).build();
    weakPasswordChecker = new WeakPasswordChecker(weakPassOption);
    pwd = "aAdifhaa#";
    assertTrue(weakPasswordCheckerStd.check(pwd));
    assertFalse(weakPasswordChecker.check(pwd));

    weakPassOption = WeakPassOption.builder().containSpecialCharacters(false).build();
    weakPasswordChecker = new WeakPasswordChecker(weakPassOption);
    pwd = "aAdifhh1fj";
    assertTrue(weakPasswordCheckerStd.check(pwd));
    assertFalse(weakPasswordChecker.check(pwd));


  }
}
/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.tools;

import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-10-16 16:45
 */
public class WeakPasswordChecker {

  public static final Logger log = Logger.getLogger(WeakPasswordChecker.class.toString());

  /*
   * basic check rule
   * 1 contains lowercase letters
   * 2, containing capital letters
   * 3, contains special symbols
   * 4, containing numbers
   * */
  private Pattern PATTERN_BASIC;

  /** Duplicate character */
  private Pattern PATTERN_DUPLICATE_CHARACTER;

  /** Continuous number in ascending order */
  private Pattern PATTERN_CONTINUOUS_NUMBER_ASC;

  /** Continuous number in ascending order */
  private Pattern PATTERN_CONTINUOUS_NUMBER_DESC;

  /** Continuous english alphabet in ascending order */
  private Pattern PATTERN_CONTINUOUS_ENGLISH_ALPHABET_ASC;

  /** Continuous english alphabet in descending order */
  private Pattern PATTERN_CONTINUOUS_ENGLISH_ALPHABET_DESC;
  /** Keyboard horizontal continuous chars in ascending order */
  private Pattern PATTERN_KEYBOARD_HORIZONTAL_CONTINUOUS_CHARS;

  /** Keyboard vertical continuous chars in ascending order */
  private Pattern PATTERN_KEYBOARD_VERTICAL_CONTINUOUS_CHARS;

  private WeakPassOption option;

  public WeakPasswordChecker() {
    this(WeakPassOption.builder().build());
  }

  public WeakPasswordChecker(WeakPassOption option) {
    StringJoiner stringJoiner = new StringJoiner("", "^", "$");
    stringJoiner.add("(?=.*[a-z])");

    if (option.containSpecialUppercaseLetter) {
      stringJoiner.add("(?=.*[A-Z])");
    }
    if (option.containSpecialNumbers) {
      stringJoiner.add("(?=.*\\d)");
    }

    if (option.containSpecialCharacters) {
      stringJoiner.add("(?=.*[@#$!%*?&])");
    }
    stringJoiner.add(
        "[A-Za-z\\d@#$!%*?&]{" + option.getMinLength() + "," + option.getMaxLength() + "}");
    PATTERN_BASIC = Pattern.compile(stringJoiner.toString());

    PATTERN_DUPLICATE_CHARACTER =
        Pattern.compile("([0-9a-zA-Z])\\1{" + (option.getMinDuplicateChars() - 1) + "}");

    PATTERN_CONTINUOUS_NUMBER_ASC =
        Pattern.compile(
            "(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){"
                + (option.getMinContinuousChars() - 1)
                + "}");

    PATTERN_CONTINUOUS_NUMBER_DESC =
        Pattern.compile(
            "(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){"
                + (option.getMinContinuousChars() - 1)
                + "}");

    PATTERN_CONTINUOUS_ENGLISH_ALPHABET_ASC =
        Pattern.compile(
            "(?:a(?=b)|b(?=c)|c(?=d)|d(?=e)|e(?=f)|f(?=g)|g(?=h)|h(?=i)|i(?=j)|j(?=k)|k(?=l)|l(?=m)|m(?=n)|n(?=o)|o(?=p)|p(?=q)|q(?=r)|r(?=s)|s(?=t)|t(?=u)|u(?=v)|v(?=w)|w(?=x)|x(?=y)|y(?=z)){"
                + (option.getMinContinuousChars() - 1)
                + "}");

    PATTERN_CONTINUOUS_ENGLISH_ALPHABET_DESC =
        Pattern.compile(
            "(?:z(?=y)|y(?=x)|x(?=w)|w(?=v)|v(?=u)|u(?=t)|t(?=s)|s(?=r)|r(?=q)|q(?=p)|p(?=o)|o(?=n)|n(?=m)|m(?=l)|l(?=k)|k(?=j)|j(?=i)|i(?=h)|h(?=g)|g(?=f)|f(?=e)|e(?=d)|d(?=c)|c(?=b)|b(?=a)){"
                + (option.getMinContinuousChars() - 1)
                + "}");
    PATTERN_KEYBOARD_HORIZONTAL_CONTINUOUS_CHARS =
        Pattern.compile(
            "(?:q(?=w)|w(?=e)|e(?=r)|r(?=t)|t(?=y)|y(?=u)|u(?=i)|i(?=o)|o(?=p)|a(?=s)|s(?=d)|d(?=f)|f(?=g)|g(?=h)|h(?=j)|j(?=k)|k(?=l)|z(?=x)|x(?=c)|c(?=v)|v(?=b)|b(?=n)|n(?=m)){"
                + (option.getMinContinuousChars() - 1)
                + "}");

    PATTERN_KEYBOARD_VERTICAL_CONTINUOUS_CHARS =
        Pattern.compile(
            "(?:1(?=q)|q(?=a)|a(?=z)|2(?=w)|w(?=s)|s(?=x)|3(?=e)e(?=d)|d(?=c)|4(?=r)|r(?=f)|f(?=v)|5(?=t)|t(?=g)|g(?=b)|6(?=y)|y(?=h)|h(?=n)|7(?=u)|u(?=j)|j(?=m)|8(?=i)|i(?=k)|k(?=,)|9(?=o)|o(?=l)|l(?=.)|0(?=p)|p(?=;)|;(?=/)){"
                + (option.getMinContinuousChars() - 1)
                + "}");

    this.option = option;
  }

  /**
   * @param password password
   * @return boolean
   * @title check
   * @description isWeak
   * @author BiJi'an
   * @date 2023-10-17 00:20
   */
  public boolean check(String password) {

    if (PATTERN_BASIC.matcher(password).matches()) {

      // duplicate chars
      boolean isWeak =
          option.enableDuplicateCheck && PATTERN_DUPLICATE_CHARACTER.matcher(password).find();

      if (!isWeak && option.enableContinuousNumberCheck) { // number asc
        isWeak = PATTERN_CONTINUOUS_NUMBER_ASC.matcher(password).find();
      }
      if (!isWeak && option.enableContinuousNumberCheck) { // number desc
        isWeak = PATTERN_CONTINUOUS_NUMBER_DESC.matcher(password).find();
      }
      if (!isWeak && option.enableContinuousEnglishAlphabetCheck) { // english alphabet asc
        isWeak = PATTERN_CONTINUOUS_ENGLISH_ALPHABET_ASC.matcher(password).find();
      }
      if (!isWeak && option.enableContinuousEnglishAlphabetCheck) { // english alphabet desc
        isWeak = PATTERN_CONTINUOUS_ENGLISH_ALPHABET_DESC.matcher(password).find();
      }
      if (!isWeak && option.enableKeyboardContinuousCharCheck) { // keyboard horizontal letters
        isWeak = PATTERN_KEYBOARD_HORIZONTAL_CONTINUOUS_CHARS.matcher(password).find();
      }
      if (!isWeak && option.enableKeyboardContinuousCharCheck) { // keyboard vertical letters
        isWeak = PATTERN_KEYBOARD_VERTICAL_CONTINUOUS_CHARS.matcher(password).find();
      }

      return isWeak;
    }
    return true;
  }

  @Builder
  @Data
  public static class WeakPassOption {

    @Default private int minLength = 6;
    @Default private int maxLength = 16;
    @Default int minDuplicateChars = 3;
    @Default int minContinuousChars = 3;
    @Default private boolean containSpecialCharacters = true;
    @Default private boolean containSpecialNumbers = true;
    @Default private boolean containSpecialUppercaseLetter = true;

    @Default private boolean enableDuplicateCheck = true;

    @Default private boolean enableContinuousNumberCheck = true;

    @Default private boolean enableContinuousEnglishAlphabetCheck = true;

    @Default private boolean enableKeyboardContinuousCharCheck = true;
  }
}

package io.github.kylinhunter.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-16 21:03
 **/
public class TestReg {
    private static final Pattern PATTERN_PROP_NAME = Pattern.compile("^(\\s*-*\\s*)(\\w+-*\\w+)\\s*:");

    public static void main(String[] args) {
        Matcher matcher = PATTERN_PROP_NAME.matcher(" -   my-money: 4");
        if (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("group(" + i + "):" + matcher.group(i));

            }
            System.out.println(matcher.replaceAll(matcher.group(1) + matcher.group(2) + ":"));
        }
    }
}

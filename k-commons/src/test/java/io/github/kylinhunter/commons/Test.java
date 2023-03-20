package io.github.kylinhunter.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 21:03
 **/
public class Test {
    private static final Pattern PATTERN_PROP_NAME = Pattern.compile("^(\\s*-*\\s*)(\\w+-*\\w+)\\s*:");

    public static void main(String[] args) {
        String str="";
        String a[]=str.split("\\.");
        String a1[]=  StringUtils.split(str,".");

        str="1";
        String b[]=str.split("\\.");

        String b1[]=  StringUtils.split(str,".");
        str="1.2";
        String c[]=str.split("\\.");
        String c1[]=  StringUtils.split(str,".");

        str=null;
//        String d[]=str.split("\\.");
        String d1[]=  StringUtils.split(str,".");

        System.out.println();
    }
}

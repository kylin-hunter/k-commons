package io.github.kylinhunter.commons.yaml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.name.CamelToSnake;
import io.github.kylinhunter.commons.name.SnakeFormat;
import io.github.kylinhunter.commons.name.SnakeToCamel;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-08 15:20
 **/
public class DefaultLoadCorrector implements LoadCorrector {
    private static final Pattern PATTERN_PROP_NAME = Pattern.compile("^(\\s*-*\\s*)(\\w+-*\\w+)\\s*:");
    private static final SnakeToCamel SNAKE_TO_CAMEL = CF.get(SnakeToCamel.class);
    private static final CamelToSnake CAMEL_TO_SNAKE = CF.get(CamelToSnake.class);

    @Override
    public String correct(String text, YamlType yamlType) {
        String[] lines = text.split("(\\r?\\n)+");
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(correntLine(line, yamlType)).append("\n");
        }
        return stringBuilder.toString();

    }

    private String correntLine(String line, YamlType yamlType) {
        Matcher matcher = PATTERN_PROP_NAME.matcher(line);
        if (matcher.find()) {
            String group = matcher.group();
            if (matcher.groupCount() > 1) {
                String group1 = matcher.group(1);
                String group2 = matcher.group(2);
                if (yamlType == YamlType.CAMLE) {
                    return matcher.replaceAll(group1 + SNAKE_TO_CAMEL.convert(group2) + ":");
                } else if (yamlType == YamlType.SNAKE_UNDERSCORE) {
                    return matcher
                            .replaceAll(group1 + CAMEL_TO_SNAKE.convert(group2, SnakeFormat.LOWWER_UNDERSCORE) + ":");
                } else {
                    return matcher.replaceAll(group1 + CAMEL_TO_SNAKE.convert(group2, SnakeFormat.LOWWER_HYPHEN) + ":");
                }
            }

        }
        return line;
    }

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

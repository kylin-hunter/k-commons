package io.github.kylinhunter.commons.generator;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 15:09
 **/
@Data
public class User {
    private long id;
    private String name;
    private LocalDateTime birth;
    private Integer age;
    private Double money;

}

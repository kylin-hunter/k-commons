package io.github.kylinhunter.commons.generator.context.bean.clazz;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 19:52
 **/
@Data
public class FieldMeta {
    private String name;
    private String type;
    private String comment;
    private Snippets customSnippets = new Snippets();

}

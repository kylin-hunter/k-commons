package io.github.kylinhunter.commons.generator.context.bean.clazz;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 19:52
 */
@Getter
@Setter
public class FieldInfo {
  private String name;
  private String columnName;
  private String type;
  private String fullType;
  private String comment;
  private Snippets snippets = new Snippets();

  /**
   * @param location location
   * @param snippets snippets
   * @return void
   * @title addSnippet
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:52
   */
  public void addSnippet(String location, String snippets) {
    this.snippets.add(location, snippets);
  }

  /**
   * @param location location
   * @return java.lang.String
   * @title snippets
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:52
   */
  public String snippets(String location) {
    return this.snippets.toString(location);
  }
}

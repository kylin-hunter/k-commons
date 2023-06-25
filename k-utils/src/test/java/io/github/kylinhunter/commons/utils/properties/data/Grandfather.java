package io.github.kylinhunter.commons.utils.properties.data;

import java.util.StringJoiner;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 01:31
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grandfather {

  @EqualsAndHashCode.Include
  private String name;
  @EqualsAndHashCode.Include
  private Father father;

  @Override
  public String toString() {
    return new StringJoiner("\n", Grandfather.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("father=" + father)
        .toString();
  }
}

package io.github.kylinhunter.commons.name;

import java.io.Serializable;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-21 19:43
 */
@Data
public class NamePair implements Serializable {
  private String camel;
  private String snake;
}

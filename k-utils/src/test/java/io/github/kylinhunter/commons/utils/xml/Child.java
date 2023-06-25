package io.github.kylinhunter.commons.utils.xml;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-16 15:29
 */
@Data
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
public class Child {

  private int value1;
  private int value2;
}

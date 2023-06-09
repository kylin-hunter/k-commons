package o.github.kylinhunter.commons.utils.xml;

import java.util.List;
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
public class Parent {
  private int value1;
  private int value2;
  private List<Child> children;
}

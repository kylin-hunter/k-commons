package o.github.kylinhunter.commons.utils.properties.data;

import java.util.List;
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
public class Father {
  @EqualsAndHashCode.Include private String name;

  @EqualsAndHashCode.Include private Son son;

  @EqualsAndHashCode.Include private List<Son> sonList;
  @EqualsAndHashCode.Include private Son[] sonArrays;

  @Override
  public String toString() {
    StringJoiner stringJoiner = new StringJoiner("\n", Father.class.getSimpleName() + "[", "]");
    stringJoiner.add("name='" + name + "'").add("son=" + son);
    if (sonList != null) {
      for (int i = 0; i < sonList.size(); i++) {
        stringJoiner.add("sonList[" + i + "]=" + sonList.get(i));
      }
    }
    if (sonArrays != null) {
      for (int i = 0; i < sonArrays.length; i++) {
        stringJoiner.add("sonArrays[" + i + "]=" + sonArrays[i]);
      }
    }

    return stringJoiner.toString();
  }
}

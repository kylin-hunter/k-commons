package io.github.kylinhunter.commons.utils.properties.data;

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
public class Son {

  @EqualsAndHashCode.Include
  private String name;
  @EqualsAndHashCode.Include
  private GrandSon[] grandSonArr;
  @EqualsAndHashCode.Include
  private List<GrandSon> grandSonList;

  @Override
  public String toString() {
    StringJoiner stringJoiner = new StringJoiner("\n ", Son.class.getSimpleName() + "[", "]");
    stringJoiner.add("name='" + name + "'");

    if (grandSonList != null) {
      for (int i = 0; i < grandSonList.size(); i++) {
        stringJoiner.add("grandSonList[" + i + "]=" + grandSonList.get(i));
      }
    }
    if (grandSonArr != null) {
      for (int i = 0; i < grandSonArr.length; i++) {
        stringJoiner.add("grandSonArr[" + i + "]=" + grandSonArr[i]);
      }
    }
    return stringJoiner.toString();
  }
}

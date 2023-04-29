package io.github.kylinhunter.commons.properties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 10:50
 */
@Data
public class PropFiled implements Comparable<PropFiled> {
  private String objecId;
  private String name;
  private String fullName;
  private Object value;
  private boolean bean;
  protected int arrIndex = -1;

  private static final Pattern pattern = Pattern.compile("\\[(\\d*)]");

  public PropFiled(String fullName) {
    this(fullName, true, null);
  }

  public PropFiled(String fullName, Object value) {
    this(fullName, false, value);
  }

  private PropFiled(String fullName, boolean bean, Object value) {
    this.fullName = fullName;
    this.bean = bean;
    this.value = value;

    int index = fullName.lastIndexOf(".");
    if (index > 0) {
      this.objecId = fullName.substring(0, index);

    } else {
      this.objecId = PropObject.ROOT_ID;
    }

    index = fullName.lastIndexOf(".");
    if (index > 0) {
      this.name = fullName.substring(index + 1);
    } else {
      this.name = fullName;
    }

    index = this.name.lastIndexOf("[");
    if (index > 0) {
      Matcher matcher = pattern.matcher(name.substring(index));
      if (matcher.find()) {
        this.arrIndex = Integer.parseInt(matcher.group(1));
      }

      this.name = this.name.substring(0, index);
    }
  }

  @Override
  public int compareTo(PropFiled o) {
    return this.getFullName().compareTo(o.fullName);
  }
}

/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.utils.properties;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.utils.bean.info.BeanIntrospector;
import io.github.kylinhunter.commons.utils.bean.info.BeanIntrospectors;
import io.github.kylinhunter.commons.utils.bean.info.ExPropertyDescriptor;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:19
 */
@Data
public class PropObject implements Comparable<PropObject> {

  private String objecId;
  private String parentName;
  protected String parentId;
  protected int level;
  public static final String ROOT_ID = "#";
  public Class<?> clazz;
  private BeanIntrospector beanIntrospector;
  private ExPropertyDescriptor propertyDescriptor;
  private static final Pattern pattern = Pattern.compile("\\[(\\d*)]");
  private List<PropFiled> propFileds = ListUtils.newArrayList();
  private Object obj;
  protected int arrIndex = -1;

  /**
   * @param objecId objecId
   * @return java.lang.String
   * @title getId
   * @description
   * @author BiJi'an
   * @date 2023-04-01 15:20
   */
  public PropObject(String objecId) {
    this(objecId, null);
  }

  public PropObject(String objecId, Object obj) {
    this.objecId = objecId;

    if (ROOT_ID.equals(objecId)) {
      this.level = 1;
      this.parentName = null;
      this.parentId = null;
    } else {

      this.level = objecId.split("\\.").length + 1;
      int index = objecId.lastIndexOf(".");
      if (index > 0) {
        this.parentId = objecId.substring(0, index);
        this.parentName = objecId.substring(index + 1);

        index = this.parentId.lastIndexOf("[");
        if (index > 0) {
          Matcher matcher = pattern.matcher(parentId.substring(index));
          if (matcher.find()) {
            this.arrIndex = Integer.parseInt(matcher.group(1));
          }
        }
        index = this.parentName.lastIndexOf("[");
        if (index > 0) {
          this.parentName = this.parentName.substring(0, index);
        }

      } else {
        this.parentId = ROOT_ID;
        this.parentName = this.objecId;
      }
    }

    if (obj != null) {
      this.setObj(obj);
    }
  }

  /**
   * @param obj obj
   * @return void
   * @title setObj
   * @description
   * @author BiJi'an
   * @date 2023-03-31 01:30
   */
  public void setObj(Object obj) {
    this.obj = obj;
    this.clazz = obj.getClass();
    this.beanIntrospector = BeanIntrospectors.get(this.clazz);
  }

  /**
   * @return boolean
   * @title isRoot
   * @description
   * @author BiJi'an
   * @date 2023-04-01 00:59
   */
  public boolean isRoot() {
    return ROOT_ID.equals(objecId);
  }

  /**
   * @param propFiled propFiled
   * @return void
   * @title addField
   * @description
   * @author BiJi'an
   * @date 2023-04-01 00:59
   */
  public void addField(PropFiled propFiled) {
    this.propFileds.add(propFiled);
  }

  /**
   * @see Comparable#compareTo
   */
  @Override
  public int compareTo(PropObject o) {
    int i = this.level - o.level;
    if (i == 0) {
      return this.objecId.compareTo(o.objecId);
    }
    return i;
  }

  public void fieldResort() {
    Collections.sort(this.propFileds);
  }
}

package io.github.kylinhunter.commons.bean.info;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 02:29
 */
public enum ExPropType {
  UNKNOWN,
  PRIMITIVE_OR_WRAPPER,
  STRING,
  ARRAY, // a array can be  recursive
  LIST, // a list  can be  recursive
  NON_JDK_TYPE, //  a peoperty can be recursive
}

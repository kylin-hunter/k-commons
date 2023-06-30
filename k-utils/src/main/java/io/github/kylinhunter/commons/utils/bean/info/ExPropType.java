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
package io.github.kylinhunter.commons.utils.bean.info;

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

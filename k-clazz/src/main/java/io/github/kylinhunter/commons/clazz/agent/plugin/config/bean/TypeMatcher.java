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
package io.github.kylinhunter.commons.clazz.agent.plugin.config.bean;

import io.github.kylinhunter.commons.clazz.exception.AgentException;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 16:36
 */
@Getter
@Setter
public class TypeMatcher {

  private String nameStartsWith;
  private String nameContains;
  private String nameRegex;

  public <S extends NamedElement> ElementMatcher<S> toElementMatcher() {
    boolean empty = true;
    String nameStartsWith = this.getNameStartsWith();
    ElementMatcher.Junction<S> elementMatcherJunction = null;
    if (!StringUtil.isEmpty(nameStartsWith)) {
      empty = false;
      elementMatcherJunction = ElementMatchers.nameStartsWith(nameStartsWith);
    }

    String nameContains = this.getNameContains();
    if (!StringUtil.isEmpty(nameContains)) {
      empty = false;
      if (elementMatcherJunction == null) {
        elementMatcherJunction = ElementMatchers.nameContains(nameContains);
      } else {
        elementMatcherJunction =
            elementMatcherJunction.or(ElementMatchers.nameContains(nameContains));
      }
    }

    String nameRegex = this.getNameRegex();
    if (!StringUtil.isEmpty(nameRegex)) {
      empty = false;
      if (elementMatcherJunction == null) {
        elementMatcherJunction = ElementMatchers.nameMatches(nameContains);
      } else {
        elementMatcherJunction =
            elementMatcherJunction.or(ElementMatchers.nameMatches(nameContains));
      }
    }
    if (empty) {
      throw new AgentException("no  matcher found");
    }
    return elementMatcherJunction;
  }
}

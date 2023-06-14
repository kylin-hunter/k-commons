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
package o.github.kylinhunter.commons.utils.yaml;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.io.IOUtil;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.name.NameRule;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.yaml.snakeyaml.Yaml;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-01 22:01
 */
public class YamlHelper {

  private static KeyCorrector defaultKeyCorrector = new DefaultKeyCorrector();

  /**
   * @param keyCorrector keyCorrector
   * @return void
   * @title resetKeyCorrector
   * @description
   * @author BiJi'an
   * @date 2023-03-19 00:39
   */
  public static void resetDefaultKeyCorrector(KeyCorrector keyCorrector) {
    defaultKeyCorrector = keyCorrector;
  }

  /**
   * @param clazz clazz
   * @param path path
   * @return T
   * @title loadFromClassPath
   * @description
   * @author BiJi'an
   * @date 2022-11-01 22:01
   */
  public static <T> T loadFromPath(Class<T> clazz, String path) {
    return loadFromPath(clazz, path, null);
  }

  /**
   * @param clazz clazz
   * @param path path
   * @return T
   * @title loadFromClassPath
   * @description
   * @author BiJi'an
   * @date 2022-11-01 22:01
   */
  public static <T> T loadFromPath(Class<T> clazz, String path, NameRule nameRule) {
    try (InputStream inputStream = ResourceHelper.getInputStream(path)) {
      if (inputStream == null) {
        throw new ParamException(" inputStream is null ,invalid path: " + path);
      }

      String text = IOUtil.toString(inputStream, StandardCharsets.UTF_8);

      return loadFromText(clazz, text, nameRule);

    } catch (KRuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new ParamException("loadFromClassPath error ,invalid path: " + path, e);
    }
  }

  /**
   * @param clazz clazz
   * @param text text
   * @return T
   * @title loadFromText
   * @description
   * @author BiJi'an
   * @date 2022-11-01 22:01
   */
  public static <T> T loadFromText(Class<T> clazz, String text) {
    return loadFromText(clazz, text, null);
  }

  /**
   * @param clazz clazz
   * @param text text
   * @return T
   * @title loadFromText
   * @description
   * @author BiJi'an
   * @date 2022-11-01 22:01
   */
  public static <T> T loadFromText(Class<T> clazz, String text, NameRule nameRule) {
    try {

      text = defaultKeyCorrector.correct(text, nameRule);
      return new Yaml().loadAs(text, clazz);

    } catch (Exception e) {
      throw new ParamException("loadFromText error ,invalid text: " + text, e);
    }
  }

  /**
   * @param obj obj
   * @return java.lang.String
   * @title dumpAsMap
   * @description
   * @author BiJi'an
   * @date 2023-02-04 00:09
   */
  public static String dumpAsMap(Object obj) {
    return dumpAsMap(obj, null);
  }

  /**
   * @param obj obj
   * @param nameRule nameRule
   * @return java.lang.String
   * @title dumpAsMap
   * @description
   * @author BiJi'an
   * @date 2023-02-04 00:20
   */
  public static String dumpAsMap(Object obj, NameRule nameRule) {
    try {
      String text = new Yaml().dumpAsMap(obj);

      return defaultKeyCorrector.correct(text, nameRule);

    } catch (Exception e) {
      throw new ParamException("dumpAsMap error ", e);
    }
  }
}

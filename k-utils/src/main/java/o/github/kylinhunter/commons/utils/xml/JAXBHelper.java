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
package o.github.kylinhunter.commons.utils.xml;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-10 14:48
 */
public class JAXBHelper {
  private static final ThreadLocal<Map<Class<?>, Marshaller>> marshallers =
      ThreadLocal.withInitial(HashMap::new);
  private static final ThreadLocal<Map<Class<?>, Unmarshaller>> unmarshallers =
      ThreadLocal.withInitial(HashMap::new);

  /**
   * @param obj obj
   * @return java.lang.String
   * @title toXml
   * @description
   * @author BiJi'an
   * @date 2022-12-16 15:19
   */
  public static String marshal(Object obj) {
    try {
      StringWriter sw = new StringWriter();
      getMarshaller(obj.getClass()).marshal(obj, sw);
      return sw.toString();
    } catch (JAXBException e) {
      throw new GeneralException("toXml error", e);
    }
  }

  /**
   * @param clazz clazz
   * @param xml xml
   * @return T
   * @title toBean
   * @description
   * @author BiJi'an
   * @date 2022-12-16 16:19
   */
  @SuppressWarnings("unchecked")
  public static <T> T unmarshal(Class<T> clazz, String xml) {
    try {
      return (T) getUnmarshaller(clazz).unmarshal(new StringReader(xml));
    } catch (JAXBException e) {
      throw new GeneralException("toXml error", e);
    }
  }

  /**
   * @param clazz clazz
   * @return javax.xml.bind.Marshaller
   * @title getMarshaller
   * @description
   * @author BiJi'an
   * @date 2022-12-16 15:50
   */
  private static Marshaller getMarshaller(Class<?> clazz) {
    final Map<Class<?>, Marshaller> marshallerMap = marshallers.get();
    return marshallerMap.computeIfAbsent(
        clazz,
        (k) -> {
          try {
            JAXBContext jaxbContext = JAXBContext.newInstance(k);
            return jaxbContext.createMarshaller();
          } catch (Exception e) {
            throw new GeneralException("create marshaller error", e);
          }
        });
  }

  /**
   * @param clazz clazz
   * @return javax.xml.bind.Unmarshaller
   * @title getUnmarshaller
   * @description
   * @author BiJi'an
   * @date 2022-12-16 16:20
   */
  private static Unmarshaller getUnmarshaller(Class<?> clazz) {
    final Map<Class<?>, Unmarshaller> marshallerMap = unmarshallers.get();
    return marshallerMap.computeIfAbsent(
        clazz,
        (k) -> {
          try {
            JAXBContext jaxbContext = JAXBContext.newInstance(k);
            return jaxbContext.createUnmarshaller();
          } catch (Exception e) {
            throw new GeneralException("create unmarshaller error", e);
          }
        });
  }
}

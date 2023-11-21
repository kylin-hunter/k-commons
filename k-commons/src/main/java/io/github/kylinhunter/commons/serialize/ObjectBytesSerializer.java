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
package io.github.kylinhunter.commons.serialize;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.exception.embed.InternalException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-10 00:58
 */
public class ObjectBytesSerializer {

  private static final byte[] EMPTY_BYTES = new byte[0];

  /**
   * @param obj obj
   * @return byte[]
   * @title serialize
   * @description
   * @author BiJi'an
   * @date 2022-12-16 23:23
   */
  public static byte[] serialize(Object obj) {
    if (obj != null) {
      try (ByteArrayOutputStream bao = new ByteArrayOutputStream();
          ObjectOutputStream oo = new ObjectOutputStream(bao)) {
        oo.writeObject(obj);
        return bao.toByteArray();
      } catch (Exception e) {
        throw new InternalException("convert error", e);
      }
    } else {
      return EMPTY_BYTES;
    }
  }

  /**
   * @param obj obj
   * @return java.lang.String
   * @title serializeToString
   * @description serializeToString
   * @author BiJi'an
   * @date 2023-11-22 01:19
   */
  public static String serializeToString(Object obj) {
    byte[] serialize = serialize(obj);
    byte[] bytes = Base64.getEncoder().encode(serialize);
    return new String(bytes, StandardCharsets.UTF_8);
  }


  /**
   * @param bytes bytes
   * @return T
   * @title deserialize
   * @description
   * @author BiJi'an
   * @date 2022-12-16 23:23
   */
  @SuppressFBWarnings("OBJECT_DESERIALIZATION")
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(byte[] bytes) {

    if (bytes != null && bytes.length > 0) {
      try (ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
          ObjectInputStream in = new ObjectInputStream(bai)) {
        return (T) in.readObject();
      } catch (Exception e) {
        throw new InternalException("convert error", e);
      }
    }
    return null;
  }

  /**
   * @param text text
   * @return T
   * @title deserializeFromStr
   * @description deserializeFromStr
   * @author BiJi'an
   * @date 2023-11-22 01:22
   */
  public static <T> T deserialize(String text) {
    byte[] bytes = Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8));
    return deserialize(bytes);
  }

}

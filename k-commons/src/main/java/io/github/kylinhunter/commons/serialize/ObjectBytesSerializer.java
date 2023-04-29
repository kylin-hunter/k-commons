package io.github.kylinhunter.commons.serialize;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.exception.embed.InternalException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
}

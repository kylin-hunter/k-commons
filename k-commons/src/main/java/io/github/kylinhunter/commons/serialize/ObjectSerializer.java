package io.github.kylinhunter.commons.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.github.kylinhunter.commons.exception.embed.InternalException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-16 00:58
 **/
public class ObjectSerializer {

    public static byte[] serialize(Object obj) {
        try (ByteArrayOutputStream bao = new ByteArrayOutputStream();
             ObjectOutputStream oo = new ObjectOutputStream(bao)) {

            oo.writeObject(obj);
            return bao.toByteArray();

        } catch (Exception e) {
            throw new InternalException("convert error", e);
        }
    }

    public static <T> T deserialize(byte[] bytes) {

        try {
            ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(bai);
            return (T) in.readObject();
        } catch (Exception e) {
            throw new InternalException("convert error", e);
        }
    }
}

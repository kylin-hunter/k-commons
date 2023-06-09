package o.github.kylinhunter.commons.utils.codec;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import io.github.kylinhunter.commons.io.Charsets;

public class TestSecureRandom {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        print(SecureRandom.getInstance("SHA1PRNG"), null);
        print(SecureRandom.getInstance("SHA1PRNG"), null);
        print(SecureRandom.getInstance("SHA1PRNG"), "123");
        print(SecureRandom.getInstance("SHA1PRNG"), "123");

        print(new SecureRandom(), null);
        print(new SecureRandom(), null);
        print(new SecureRandom(), "123");
        print(new SecureRandom(), "123");

        print(SecureRandom.getInstance("NativePRNG"), null);
        print(SecureRandom.getInstance("NativePRNG"), null);
        print(SecureRandom.getInstance("NativePRNG"), "123");
        print(SecureRandom.getInstance("NativePRNG"), "123");
    }

    private static void print(SecureRandom random, String key) {
        if (key != null) {
            random.setSeed(key.getBytes(Charsets.UTF_8));
        }

        System.out.println("random with algorithm( " + random.getAlgorithm() + " ) with key:" + key);

        System.out.println(random.nextDouble());
        System.out.println(random.nextDouble());
    }
}

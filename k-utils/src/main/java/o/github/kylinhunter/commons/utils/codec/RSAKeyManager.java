package o.github.kylinhunter.commons.utils.codec;

import io.github.kylinhunter.commons.cache.guava.Cache;
import io.github.kylinhunter.commons.exception.embed.CryptException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-20 21:40
 */
public class RSAKeyManager {
  public static final String ALGORITHM_RSA = "RSA";
  private static final int DEFAULT_KEY_SIZE = 1024;
  @Getter private final RSAPrivateKey defaultPrivateKey;
  @Getter private final RSAPublicKey defaultPubKey;

  private final Cache<RSAPublicKey> rsaPublicKeyCache = new RSAPublicKeyCache();

  private final Cache<RSAPrivateKey> rsaPrivateKeyCache = new RSAPrivateKeyCache();

  public RSAKeyManager() {
    RSAKeyPair rsaKeyPair = this.newKeyPair();
    this.defaultPubKey = (RSAPublicKey) rsaKeyPair.getKeyPair().getPublic();
    this.defaultPrivateKey = (RSAPrivateKey) rsaKeyPair.getKeyPair().getPrivate();
  }

  /**
   * @param keysize keysize
   * @return o.github.kylinhunter.commons.utils.codec.RSAUtils.RSAKeyPair
   * @title generateRSAKeyPair
   * @description
   * @author BiJi'an
   * @date 2022-06-22 01:27
   */
  public RSAKeyPair newKeyPair(int keysize) {
    try {

      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
      keyPairGen.initialize(keysize);
      KeyPair keyPair = keyPairGen.generateKeyPair();

      return new RSAKeyPair(keyPair);
    } catch (NoSuchAlgorithmException e) {
      throw new CryptException("generateRSAKeyPair error", e);
    }
  }

  public RSAKeyPair newKeyPair() {
    return this.newKeyPair(DEFAULT_KEY_SIZE);
  }

  /**
   * @param publicKey publicKey
   * @return java.security.interfaces.RSAPublicKey
   * @title restorePublicKey
   * @description
   * @author BiJi'an
   * @date 2022-06-22 01:28
   */
  public RSAPublicKey toPublicKey(String publicKey) {

    return this.rsaPublicKeyCache.get(publicKey);
  }

  /**
   * @param publicKey publicKey
   * @return java.security.interfaces.RSAPrivateKey
   * @title toPrivateKey
   * @description
   * @author BiJi'an
   * @date 2022-11-20 23:29
   */
  public RSAPrivateKey toPrivateKey(String publicKey) {

    return this.rsaPrivateKeyCache.get(publicKey);
  }

  @Getter
  @RequiredArgsConstructor
  public static class RSAKeyPair {
    private final KeyPair keyPair;
    private final String publicKey;
    private final String privateKey;

    public RSAKeyPair(KeyPair keyPair) {
      this.keyPair = keyPair;
      this.publicKey = Base64Utils.encodeToString(keyPair.getPublic().getEncoded());
      this.privateKey = Base64Utils.encodeToString(keyPair.getPrivate().getEncoded());
    }
  }
}

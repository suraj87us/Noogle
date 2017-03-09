package com.giyer.noogleplatform.managers.encryption;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by giyer7 on 3/8/17.
 */

public class EncryptionImpl implements Encryption {

    public enum AlgorithmSpec {
        AES("AES/CBC/PKCS5Padding", "AES", 128, true, Charset.forName(UTF8)), RSA("RSA/ECB/PKCS1Padding", "RSA", 3072, false, null), SHA256("SHA-256", null, 0, false, Charset.forName(UTF8));
        String algorithm;
        String keyType;
        int keySize;
        boolean padded;
        Charset messageCharset;

        private AlgorithmSpec(String algorithm, String keyType, int keySize, boolean padded, Charset messageCharset) {
            this.algorithm = algorithm;
            this.keyType = keyType;
            this.keySize = keySize;
            this.padded = padded;
            this.messageCharset = messageCharset;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public String getKeyType() {
            return keyType;
        }

        public int getKeySize() {
            return keySize;
        }

        public boolean isPadded() {
            return padded;
        }

        public Charset getMessageCharset() {
            return messageCharset;
        }

        public String toString() {
            return getAlgorithm();
        }
    }

    public static final String UTF8 = "UTF-8";
    private static final int IV_BYTES = 16;
    private static byte[] randomBytes = new byte[IV_BYTES];

    private static byte[] encrypt(String message, Key key, AlgorithmSpec alg, boolean fromBase64) {
        try {
            Cipher cipher = Cipher.getInstance(alg.getAlgorithm());
            if (alg.padded) {
                cipher.init(Cipher.ENCRYPT_MODE, key, getIvParamSpec(true));
                byte[] original = cipher.doFinal(messageToBytes(message, alg, fromBase64));
                return padBytes(original, cipher.getIV());
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return cipher.doFinal(messageToBytes(message, alg, fromBase64));
            }
        } catch (Exception e) {
            throw TigerRoundhouseThrow.executeSpin(new ChuckNorrisException(e.getMessage()));
        }
    }

    private static String decrypt(byte[] encrypted, Key key, AlgorithmSpec alg, boolean toBase64) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(alg.getAlgorithm());
        if (alg.padded)
            cipher.init(Cipher.DECRYPT_MODE, key, getIvParamSpec(false));
        else
            cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] original = cipher.doFinal(encrypted);
        if (alg.padded)
            original = unpadBytes(original);
        return bytesToMessage(original, alg, toBase64);
    }

    private static IvParameterSpec getIvParamSpec(boolean randomize) {
        if (randomize)
            new Random().nextBytes(randomBytes);
        return new IvParameterSpec(randomBytes);
    }

    private static byte[] messageToBytes(String message, AlgorithmSpec alg, boolean fromBase64) throws IOException {
        Charset charset = alg.getMessageCharset();
        if (charset != null && !fromBase64)
            return message.getBytes(charset.displayName());
        else if (fromBase64)
            return base64Decode(message);
        else
            return message.getBytes();
    }

    private static String bytesToMessage(byte[] original, AlgorithmSpec alg, boolean toBase64) throws GeneralSecurityException, UnsupportedEncodingException {
        Charset charset = alg.getMessageCharset();
        if (charset != null && !toBase64)
            return new String(original, charset.displayName());
        else if (toBase64)
            return base64Encode(original);
        else
            return new String(original);
    }

    private static byte[] padBytes(byte[] original, byte[] iv) {
        byte[] out = new byte[original.length + iv.length];
        System.arraycopy(iv, 0, out, 0, iv.length);
        System.arraycopy(original, 0, out, iv.length, original.length);
        return out;
    }

    private static byte[] unpadBytes(byte[] original) {
        byte[] out = new byte[original.length - IV_BYTES];
        System.arraycopy(original, IV_BYTES, out, 0, out.length);
        return out;
    }


    public static class ChuckNorrisException extends Throwable {
        private static final long serialVersionUID = 4208370645766470147L;

        public ChuckNorrisException(String string) {
            super(string);
        }
    }

    public static class TigerRoundhouseThrow {
        public static RuntimeException executeSpin(Throwable t) {
            throw TigerRoundhouseThrow.<RuntimeException>throwGivenThrowable(t);
        }

        @SuppressWarnings("unchecked")
        private static <T extends Throwable> RuntimeException throwGivenThrowable(Throwable t) throws T {
            throw (T) t;
        }
    }

    public static String base64Encode(byte[] key) {
        return Base64.encodeToString(key, Base64.DEFAULT);
    }

    public static byte[] base64Decode(String key) throws IOException {
        return Base64.decode(key, Base64.DEFAULT);
    }

    /**
     * This method is being used for decryption the user name with saltKey which is being used to encrypt it.
     *
     * @param message - encrypted user name which we have to decrypt which is in string format.
     * @param saltKey - key which was used to encrypt user name now it will decrypt the user name which is in string format.
     * @param alg     - type of algorithm we are using such as AES,RSA etc.
     * @return string which is a decrypted user name.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String decryptWithSalt(String message, String saltKey, AlgorithmSpec alg) throws GeneralSecurityException, IOException {
        String dec = decrypt(base64Decode(message), convertToSecKey(saltKey), alg, false);
        return dec;
    }

    /**
     * Converting string type in SecretKey type of object. Generating the SecretkeySpec object which implements SecretKey interface so that it can be used as session key in encryption and decryption methods.
     *
     * @param saltKey - key which is being used to encrypt and decrypt the user name having string format.
     * @return secret key object
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private static SecretKey convertToSecKey(String saltKey) throws GeneralSecurityException, IOException {
        byte[] keyBytes = base64Decode(saltKey);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

    /**
     * This method is being used for encrypting the user name with application provided key which is being generated by application dynamically
     *
     * @param message - user name which we have to encrypt which is in string format.
     * @param appKey  - application generated key which is being used to encrypt user name.
     * @param alg     - type of algorithm we are using such as AES,RSA etc.
     * @return string which is an encrypted user name.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String encryptWithAppKey(String message, String appKey, AlgorithmSpec alg) throws GeneralSecurityException, IOException {
        String enc = base64Encode(encrypt(message, convertFromStringToKey(appKey), alg, false));
        return enc;
    }

    /**
     * This method is being used to decrypt the user name with appKey which is being used to encrypt it.
     *
     * @param message - encrypted user name which we have to decrypt which is in string format.
     * @param appKey  - key which was used to encrypt user name now it will decrypt the user name which is in string format.
     * @param alg     - type of algorithm we are using such as AES,RSA etc.
     * @return string which is a decrypted user name.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String decryptWithAppKey(String message, String appKey, AlgorithmSpec alg) throws GeneralSecurityException, IOException {
        String dec = decrypt(base64Decode(message), convertFromStringToKey(appKey), alg, false);
        return dec;
    }

    /**
     * Converting string type in SecretKey type of object. Generating the SecretkeySpec object which implements SecretKey interface so that it can be used as session key in encryption and decryption methods.
     *
     * @param appKey - key which is being used to encrypt and decrypt the user name having string format.
     * @return secret key object
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private static SecretKey convertFromStringToKey(String appKey) throws GeneralSecurityException, IOException {
        byte[] key = (appKey).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // use only first 128 bit
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    public static String getHashForKey(String message, String key) throws GeneralSecurityException, IOException {
        return base64Encode(getHash(message, messageToBytes(key, AlgorithmSpec.SHA256, true)));
    }

    /*
       Added to support the getHashForKey(...)
     */
    private static byte[] getHash(String password, byte[] saltBytes) throws GeneralSecurityException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance(AlgorithmSpec.SHA256.getAlgorithm());
        digest.reset();
        digest.update(saltBytes);
        return digest.digest(password.getBytes(AlgorithmSpec.SHA256.getMessageCharset().displayName()));
    }
}

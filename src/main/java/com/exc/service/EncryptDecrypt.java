package com.exc.service;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Responsible for encrypt/decrypt private keys
 */
@Component
public class EncryptDecrypt {
    private final Logger log = LoggerFactory.getLogger(EncryptDecrypt.class);
    // todo: change me
    String key = "Bar12345Bar12345"; // 128 bit key
    String initVector = "RandomInitVector";
    Cipher cipher;
    IvParameterSpec iv;
    SecretKeySpec skeySpec;

    public EncryptDecrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException {

        iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

    }

    public synchronized String encrypt(String value) {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    public synchronized String decrypt(String encrypted) {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }
}

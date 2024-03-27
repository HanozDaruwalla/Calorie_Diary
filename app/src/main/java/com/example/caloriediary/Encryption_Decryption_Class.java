package com.example.caloriediary;

import android.os.Build;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption_Decryption_Class {

    final static String key = "jMLWNSIs2CJtpMpKxmr1rQ=="; // Replace this with your secret key;

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(key));
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }
        return "If Statement Failed";
    }

    public static String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, generateKey(key));
        byte[] encryptedBytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encryptedBytes = Base64.getDecoder().decode(encryptedText);
        }
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    private static Key generateKey(String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
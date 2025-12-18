package com.example.caloriediary.Creating_Account_And_Login;

import android.os.Build;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption_Decryption_Class {

    final static String Encrypt_Decrypt_Key = "jMLWNSIs2CJtpMpKxmr1rQ==";

    private static final String Name_Of_Algorithm = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static String Encryption_Function(String Inputted_String) throws Exception {
        Cipher Cipher_Object = Cipher.getInstance(TRANSFORMATION);
        Cipher_Object.init(Cipher.ENCRYPT_MODE, Generate_Encryption_Decryption_Key(Encrypt_Decrypt_Key));
        byte[] Encrypted_Bytes = Cipher_Object.doFinal(Inputted_String.getBytes());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(Encrypted_Bytes);
        }
        return "Encryption Failed At If Statement";
    }

    public static String Decryption_Function(String Inputted_Encrypted_String) throws Exception {
        Cipher Cipher_Object = Cipher.getInstance(TRANSFORMATION);
        Cipher_Object.init(Cipher.DECRYPT_MODE, Generate_Encryption_Decryption_Key(Encrypt_Decrypt_Key));
        byte[] Encrypted_Bytes = new byte[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Encrypted_Bytes = Base64.getDecoder().decode(Inputted_Encrypted_String);
        }
        byte[] decryptedBytes = Cipher_Object.doFinal(Encrypted_Bytes);
        return new String(decryptedBytes);
    }

    private static Key Generate_Encryption_Decryption_Key(String Key) throws Exception {
        byte[] Bytes_Key = Key.getBytes();
        return new SecretKeySpec(Bytes_Key, Name_Of_Algorithm);
    }
}
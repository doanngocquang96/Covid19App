package com.example.database;

import android.annotation.TargetApi;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.Hex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.PSSParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class Cryptography {

    private static final String TSE_KEY_ALIAS = "TSE_DATA";

    @TargetApi(Build.VERSION_CODES.M)
    public static String encrypt(String data) throws Exception {
        byte[] srcBytes = data.getBytes(StandardCharsets.UTF_8);
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        Cipher cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        SecretKey secretKey = (SecretKey) keyStore.getKey(TSE_KEY_ALIAS, null);
        IvParameterSpec params = new IvParameterSpec("1234567890123456".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, params);
        byte[] cipherText = cipher.doFinal(srcBytes);
        return Base64Utils.encode(cipherText).replace("\n", "");
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static String decrypt(String data) throws Exception {
        byte[] src = Base64Utils.decode(data);
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        Cipher cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        SecretKey secretKey = (SecretKey) keyStore.getKey(TSE_KEY_ALIAS, null);
        IvParameterSpec params = new IvParameterSpec("1234567890123456".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
        byte[] cipherText = cipher.doFinal(src);
        return new String(cipherText);
    }


}

package com.ngts.common.e2e;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class EncryptionUtils {

    private static final String ALGORITHM = "AES";
    private static final byte[] SECRET_KEY = new byte[]{'N','G','T','S', 'S','C','M','A'};

    /**
     *
     * @param input
     * @return
     */
    public static String encrypt(String input){
        String encodedString = "";
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValue = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            encodedString = Base64.getEncoder().encodeToString(encValue);

        }catch (Exception e ){
            e.printStackTrace();
        }
        return encodedString;
    }

    /**
     *
     * @param inputValue
     * @return
     */
    public static  String decrypt(String inputValue){
        String decodedString = "";
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decValue = Base64.getDecoder().decode(inputValue);
            byte[] decVal = cipher.doFinal(decValue);
            decodedString = Base64.getEncoder().encodeToString(decVal);

        }catch (Exception e ){
            e.printStackTrace();
        }
        return decodedString;
    }

    private static Key generateKey() {
        SecretKeySpec spec = new SecretKeySpec(SECRET_KEY, ALGORITHM);
        return spec;
    }

    public static void main(String[] args) {
        System.out.println(EncryptionUtils.encrypt("RAmadoss"));

    }
}

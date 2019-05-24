package com.blogofyb.tools.img.encrypt;

import com.blogofyb.tools.img.interfaces.Encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAEncrypt implements Encrypt {
    private static volatile SHAEncrypt instance;

    private SHAEncrypt() {}

    public static SHAEncrypt getInstance() {
        if (instance == null) {
            synchronized(SHAEncrypt.class) {
                if (instance == null) {
                    instance = new SHAEncrypt();
                }
            }
        }
        return instance;
    }

    @Override
    public String encrypt(String before) {
        StringBuilder builder = new StringBuilder();
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA");
            sha.update(before.getBytes());
            for (byte b : sha.digest()) {
                builder.append(String.format("%02X", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

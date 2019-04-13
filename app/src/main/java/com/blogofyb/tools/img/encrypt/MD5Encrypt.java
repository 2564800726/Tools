package com.blogofyb.tools.img.encrypt;

import com.blogofyb.tools.img.interfaces.Encrypt;

import java.security.MessageDigest;

public class MD5Encrypt implements Encrypt {
    private volatile static MD5Encrypt md5Encrypt;

    private MD5Encrypt() {}

    public static MD5Encrypt getInstance() {
        if (md5Encrypt == null) {
            synchronized(MD5Encrypt.class) {
                if (md5Encrypt == null) {
                    md5Encrypt = new MD5Encrypt();
                }
            }
        }
        return md5Encrypt;
    }

    @Override
    public String encrypt(String before) {
        return hashKeyFromUrl(before);
    }

    private String hashKeyFromUrl(String before) {
        String cacheKey;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(before.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(before.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }
}

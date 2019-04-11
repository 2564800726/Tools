package com.blogofyb.tools.img.encrypt;

import com.blogofyb.tools.img.encrypt.interfaces.Encrypt;

import java.security.MessageDigest;

public class MD5Encrypt implements Encrypt<String, String> {
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

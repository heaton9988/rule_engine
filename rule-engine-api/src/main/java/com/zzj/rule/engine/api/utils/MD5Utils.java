package com.zzj.rule.engine.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 */
public final class MD5Utils {

    private static final Logger log = LoggerFactory.getLogger(MD5Utils.class);
    private static final ThreadLocal<MessageDigest> MESSAGE_DIGEST_CACHE = new ThreadLocal();
    private static final char[] md5Chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private MD5Utils() {
    }

    public static String getMD5Str(String str) {
        try {
            MessageDigest messagedigest = getMessageDigest();
            messagedigest.update(str.getBytes(StandardCharsets.UTF_8));
            return bufferToHex(messagedigest.digest());
        } catch (Exception var2) {
            log.error("MD5Util getMD5Str error", var2);
            throw new IllegalArgumentException("MD5Util getMD5Str error");
        }
    }

    private static MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        MessageDigest messagedigest = (MessageDigest)MESSAGE_DIGEST_CACHE.get();
        if (messagedigest == null) {
            messagedigest = MessageDigest.getInstance("MD5");
            MESSAGE_DIGEST_CACHE.set(messagedigest);
        }

        return messagedigest;
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuilder stringBuilder = new StringBuilder(2 * n);
        int k = m + n;

        for(int l = m; l < k; ++l) {
            appendHexPair(bytes[l], stringBuilder);
        }

        return stringBuilder.toString();
    }

    private static void appendHexPair(byte bt, StringBuilder stringBuilder) {
        char c0 = md5Chars[(bt & 240) >> 4];
        char c1 = md5Chars[bt & 15];
        stringBuilder.append(c0);
        stringBuilder.append(c1);
    }
}

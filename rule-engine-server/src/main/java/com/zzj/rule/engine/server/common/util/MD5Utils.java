package com.zzj.rule.engine.server.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Utils {
    private static final Logger log = LoggerFactory.getLogger(MD5Utils.class);
    private static final SingleCache<MessageDigest> MESSAGE_DIGEST_CACHE = new SingleCache<>();
    private static final char[] md5Chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private MD5Utils() {
    }

    public synchronized static String getMD5Str(String str) {
        try {
            MessageDigest messagedigest = getMessageDigest();
            messagedigest.update(str.getBytes(StandardCharsets.UTF_8));
            return bufferToHex(messagedigest.digest(), messagedigest.digest().length);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5Util getMD5Str error", e);
        }
        return "";
    }

    private static MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        MessageDigest messagedigest = MESSAGE_DIGEST_CACHE.get();
        if (messagedigest == null) {
            messagedigest = MessageDigest.getInstance("MD5");
            MESSAGE_DIGEST_CACHE.set(messagedigest);
        }

        return messagedigest;
    }


    private static String bufferToHex(byte[] bytes, int n) {
        StringBuilder stringBuilder = new StringBuilder(2 * n);

        for (int l = 0; l < n; ++l) {
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

    private static class SingleCache<T> {

        private T element;

        public void set(T messageDigest) {
            if (element != null) {
                throw new UnsupportedOperationException();
            }
            element = messageDigest;
        }

        public T get() {
            return element;
        }
    }
}

package com.zzj.rule.engine.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 */
public class IOUtils {

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int)count;
    }

    public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, 4096);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count;
        int n;
        for(count = 0L; -1 != (n = input.read(buffer)); count += (long)n) {
            output.write(buffer, 0, n);
        }
        return count;
    }

}

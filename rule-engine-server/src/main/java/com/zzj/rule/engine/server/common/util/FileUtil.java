package com.zzj.rule.engine.server.common.util;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件工具类
 *
 * @date 08/15/2021
 **/
public class FileUtil {

    public static String getFileContent(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new RuntimeException("file path is empty");
        }
        StringBuilder sb = new StringBuilder();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        assert inputStream != null;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
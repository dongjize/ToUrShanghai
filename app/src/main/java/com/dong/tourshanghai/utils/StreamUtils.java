package com.dong.tourshanghai.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class StreamUtils {

    /**
     * 输入流转化为字符串
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String readFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        is.close();
        String result = baos.toString();
        baos.close();
        return result;
    }
}

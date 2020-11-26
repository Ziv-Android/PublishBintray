package com.ziv.develop.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEquals(String str1, String str2) {
        if (isEmpty(str1)) {
            return isEmpty(str2);
        } else if (isEmpty(str2)) {
            return isEmpty(str1);
        }
        return str1.equals(str2);
    }

    public static String getStringFromFile(File file) {
        if (file == null) {
            return null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return getStringFromBufferReader(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromAssetsFile(Context context, String fileName) {
        Context applicationContext = context.getApplicationContext();
        try {
            InputStream inputStream = applicationContext.getAssets().open(fileName);
            return getStringFromInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromInputStream(InputStream stream) {
        if (stream == null) {
            return null;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        return getStringFromBufferReader(bufferedReader);
    }

    private static String getStringFromBufferReader(BufferedReader bufferedReader) {
        if (bufferedReader == null) {
            return null;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
            }
            closeStream(bufferedReader);
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            closeStream(bufferedReader);
        }
        return null;
    }

    private static void closeStream(Closeable ioStream) {
        if (ioStream != null) {
            try {
                ioStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

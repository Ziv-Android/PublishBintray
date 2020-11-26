package com.ziv.develop.utils;


import java.io.File;

public class FileUtil {
    private static final String TAG = "FileUtil";
    private static final int ERROR_FILE_SIZE = -1;

    public static long getFileSize(String path) {
        if (StringUtil.isEmpty(path)) {
            return ERROR_FILE_SIZE;
        }
        return getFileSize(new File(path));
    }

    public static long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return ERROR_FILE_SIZE;
        }
        if (file.isFile()) {
            return file.length();
        }
        if (file.isDirectory()) {
            long size = 0;
            File[] fileList = file.listFiles();
            for (File childFile : fileList) {
                size += getFileSize(childFile);
            }
            return size;
        }
        return ERROR_FILE_SIZE;
    }

    public static File createFile(String path, String fileName) {
        File parentFile = new File(path);
        if (!parentFile.exists() || !parentFile.isDirectory()) {
            deleteFile(parentFile);
            parentFile.mkdirs();
        }
        return new File(path, fileName);
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                deleteFileSafely(file);
                return;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length != 0) {
                    for (File child : files) {
                        deleteFile(child);
                    }
                }
                deleteFileSafely(file);
            }
        }
    }

    /**
     * two or more process reference the same file,
     * file was deleted, but the reference not be killed
     * so rename it to solve this system bug.
     */
    private static boolean deleteFileSafely(File file) {
        if (file != null) {
            File rename = new File(file.getAbsolutePath() + System.currentTimeMillis());
            boolean renameSuccess = file.renameTo(rename);
            if (renameSuccess) {
                LogUtil.d(TAG, "Delete file success.");
                return rename.delete();
            }
            LogUtil.e(TAG, "Delete file failed.");
        } else {
            LogUtil.e(TAG, "File not exit, delete failed.");
        }
        return false;
    }
}

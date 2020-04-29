package com.ziv.develop.utils;

import android.util.Log;

/**
 * DEBUG版本日志默认全部打开，OPEN_LOG标志位失效
 * RELEASE版本后，由OPEN_LOG控制日志是否开启
 * OPEN_MSG_CLASS_HEAD标志位单独控制是否输出详细信息头日志
 *
 * setLogLevel控制输出日志级别，一般用于日志持久化系统，默认开启DEBUG，INFO，WARN，ERROR四个级别
 *
 * @author ziv
 */
public class LogUtil {
    private static final String TAG = "LogUtil";
    private static boolean DEBUG = BuildConfig.DEBUG;

    private static final int CURRENT_STACK_INDEX = 6;

    private static final boolean OPEN_MSG_CLASS_HEAD = true;

    private static final int LOG_LEVEL_VERBOSE = Log.VERBOSE;
    private static final int LOG_LEVEL_DEBUG = Log.DEBUG;
    private static final int LOG_LEVEL_INFO = Log.INFO;
    private static final int LOG_LEVEL_WARN = Log.WARN;
    private static final int LOG_LEVEL_ERROR = Log.ERROR;
    private static final int LOG_LEVEL_ASSERT = Log.ASSERT;

    // default value
    private static boolean mOpenLog = true;
    private static int mLevel = LOG_LEVEL_ERROR;
    private static ITimeCost mTimeCost;

    /**
     * Release版本后，日志输出控制
     *
     * @param openLog 是否开启日志输出
     */
    public void setOpenLog(boolean openLog) {
        mOpenLog = openLog;
    }

    /**
     * 输出日志级别控制，默认LOG_LEVEL_ERROR = android.util.Log.ERROR
     *
     * @param logLevel 日志级别
     */
    public void setLogLevel(int logLevel){
        mLevel = logLevel;
    }

    public static void setTimeCostListener(ITimeCost listener) {
        mTimeCost = listener;
    }
    /**
     * 耗时检测——记录
     */
    public interface ITimeCost {
        /**
         * 获取耗时结果
         */
        void methodToBeTest();
    }

    /**
     * 获取方法耗时，调用前实现@{ITimeCost}接口
     */
    public static long getTimeCost() {
        if (mTimeCost == null) {
            new RuntimeException("Not has implements");
        }
        long timeMillis = System.currentTimeMillis();
        mTimeCost.methodToBeTest();
        return System.currentTimeMillis() - timeMillis;
    }

    public static void i(String tag, String msg) {
        if ((DEBUG || mOpenLog) && (mLevel >= LOG_LEVEL_INFO)) {
            if (OPEN_MSG_CLASS_HEAD) {
                Log.i(getTag(tag), getMsgHead() + msg);
            } else {
                Log.i(getTag(tag), msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if ((DEBUG || mOpenLog) && (mLevel >= LOG_LEVEL_DEBUG)) {
            if (OPEN_MSG_CLASS_HEAD) {
                Log.d(getTag(tag), getMsgHead() + msg);
            } else {
                Log.d(getTag(tag), msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if ((DEBUG || mOpenLog) && (mLevel >= LOG_LEVEL_ERROR)) {
            if (OPEN_MSG_CLASS_HEAD) {
                Log.e(getTag(tag), getMsgHead() + msg);
            } else {
                Log.e(getTag(tag), msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if ((DEBUG || mOpenLog) && (mLevel >= LOG_LEVEL_WARN)) {
            if (OPEN_MSG_CLASS_HEAD) {
                Log.w(getTag(tag), getMsgHead() + msg);
            } else {
                Log.w(getTag(tag), msg);
            }
        }
    }

    public static void processLog(String msg) {
        Log.d("ProcessLog", msg);
    }

    public static void stateLog(String msg) {
        Log.d("StateLog", msg);
    }

    public static void timeLog(long time) {
        Log.d("TimeTest", "time cost: " + (System.currentTimeMillis() - time));
    }

    /**
     * 针对网络请求结果的大日志输出控制台被截断的日志输出
     */
    public static void largeLog(String tag, String msg) {
        if (tag == null || tag.length() == 0 || msg == null || msg.length() == 0) {
            return;
        }
        int segmentSize = 3 * 1024;
        long length = msg.length();
        // 长度小于等于限制直接打印
        if (length <= segmentSize) {
            Log.d(tag, msg);
        } else {
            // 循环分段打印日志
            while (msg.length() > segmentSize) {
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.d(tag, logContent);
            }
            // 打印剩余日志
            Log.d(tag, msg);
        }
    }

    private static String getTag(String tag){
        if (tag == null || tag.trim().equals("")){
            return TAG;
        }
        return tag;
    }

    /**
     * 获取日志信息头
     * [Class.method(line)-thread-time]
     */
    private static String getMsgHead() {
        return "[" + getThreadInfo() + "->" + getCallMethodInfo() + "]: ";
    }

    /**
     * 当前方法所在线程信息，进程号
     */
    private static String getThreadInfo() {
        Thread thread = Thread.currentThread();
        String name = thread.getName();
        long id = thread.getId();

        return name + "[" +id +"]";
    }

    /**
     * 获取调用方法信息
     * stackTrace[5]当前方法所在栈
     * stackTrace[6]调用当前方法的方法所在栈信息
     */
    private static String getCallMethodInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        String info = stackTrace[CURRENT_STACK_INDEX].toString();
        int index = getUpperCaseIndex(info);
        if (index >= 0) {
            info = info.substring(index);
        }
        return info;
    }

    /**
     * 获取传入字符串的第一个大写字母所在的index
     *
     * @param info 待检测字符串
     * @return index
     */
    private static int getUpperCaseIndex(String info) {
        int length = info.length();
        int index = -1;
        for (int i = 0; i < length; i++) {
            char letter = info.charAt(i);
            if (Character.isUpperCase(letter)){
                index = i;
                break;
            }
        }
        return index;
    }
}

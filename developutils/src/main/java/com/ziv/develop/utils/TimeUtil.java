package com.ziv.develop.utils;

import android.os.SystemClock;

import com.ziv.develop.utils.server.SntpClient;

import java.util.List;

public class TimeUtil {
    private static final String TAG = "TimeUtil";
    private static final int TIMEOUT_REQUEST = 15 * 1000;

    private static String[] ntpServerPool = {
            "ntp.aliyun.com", "ntp1.aliyun.com", "ntp2.aliyun.com", "ntp3.aliyun.com", "ntp4.aliyun.com", "ntp5.aliyun.com", "ntp6.aliyun.com", "ntp7.aliyun.com",
            "time1.cloud.tencent.com", "time2.cloud.tencent.com", "time3.cloud.tencent.com", "time4.cloud.tencent.com", "time5.cloud.tencent.com",
            "cn.pool.ntp.org", "cn.ntp.org.cn", "sg.pool.ntp.org", "tw.pool.ntp.org", "jp.pool.ntp.org", "hk.pool.ntp.org", "th.pool.ntp.org",
            "time.windows.com", "time.nist.gov", "time.apple.com", "time.asia.apple.com",
            "dns1.synet.edu.cn", "news.neu.edu.cn", "dns.sjtu.edu.cn", "dns2.synet.edu.cn", "ntp.glnet.edu.cn", "s2g.time.edu.cn",
            "ntp-sz.chl.la", "ntp.gwadar.cn", "3.asia.pool.ntp.org"};

    public static void setNtpServerPool(List<String> hosts) {
        if (hosts == null || hosts.isEmpty()) {
            return;
        }
        int size = hosts.size();
        String[] serverPool = new String[size];
        for (int i = 0; i < size; i++) {
            serverPool[i] = hosts.get(i);
        }
        ntpServerPool = serverPool;
    }

    /**
     * 注：此方法需要连接网络访问服务器，故不能在UI线程直接调用
     */
    public static long getCurrentTime() {
        SntpClient sntpClient = new SntpClient();
        long time = System.currentTimeMillis();
        if (sntpClient != null) {
            for (String serverHost : ntpServerPool) {
                if (sntpClient.requestTime(serverHost, TIMEOUT_REQUEST)) {
                    time = sntpClient.getNtpTime() + SystemClock.elapsedRealtime() - sntpClient.getNtpTimeReference();
                    LogUtil.d(TAG, "TimeServer: " + serverHost + " ,return: " + time);
                    return time;
                }
            }
        }
        return time;
    }
}

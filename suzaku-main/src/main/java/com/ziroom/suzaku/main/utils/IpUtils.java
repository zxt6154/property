package com.ziroom.suzaku.main.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author libingsi
 * @date 2021/10/29 下午05:18
 */
public class IpUtils {


    public static String getLocalIp(){
        String ip = "";
        try {
            InetAddress inetAddress =InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}

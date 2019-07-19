package com.sun.snow.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/7/12
 */
public class IpUtil {

    private final static String[] IGNORE_HOSTS = new String[]{

    };

    public static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串ip值
        //取X-Forwarded-For中第一个非unknown的有效IP字符串 为请求的真实ip
        String[] ipArr = StringUtils.split(ip, ",");
        String realIp = ip;
        /*if (ipArr.length > 1) {
            for(String curIp:ipArr){
                if(isIgnoreHosts(curIp)){
                    continue;
                }else{
                    realIp = curIp;
                    break;
                }
            }
        }*/
        if("0:0:0:0:0:0:0:1".equals(realIp)){
            realIp = "127.0.0.1";
        }
        return realIp;
    }

    private static boolean isIgnoreHosts(String ip){
        boolean result = false;
        for(String ignoreHost:IGNORE_HOSTS){
            if(ignoreHost.equalsIgnoreCase(ip)){
                result = true;
                break;
            }
        }
        return result;
    }
}

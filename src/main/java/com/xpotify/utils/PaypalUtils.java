package com.xpotify.utils;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

@Log4j2
public class PaypalUtils {
    public static String getBaseURL(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if (url.toString().endsWith("/")) {
            url.append("/");
        }
        log.debug(url);
        return url.toString();
    }
}

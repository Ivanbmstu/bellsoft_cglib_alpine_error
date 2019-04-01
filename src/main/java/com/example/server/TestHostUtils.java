package com.example.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestHostUtils {

    public static String getLocalHostname() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostName();
    }
}

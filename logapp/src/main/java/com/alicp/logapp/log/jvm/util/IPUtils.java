package com.alicp.logapp.log.jvm.util;

import org.springframework.util.Assert;

import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:13
 * CopyRight: taobao
 * Descrption:
 */

public class IPUtils {

    private static Collection<InetAddress> getAllHostAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            Collection<InetAddress> addresses = new ArrayList<InetAddress>();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    addresses.add(inetAddress);
                }
            }

            return addresses;
        } catch (SocketException e) {
            throw new AlimonitorJmonitorException(e.getMessage(), e);
        }
    }

    public static Collection<String> getAllIpv4NoLoopbackAddresses() {
        Collection<String> noLoopbackAddresses = new ArrayList<String>();
        Collection<InetAddress> allInetAddresses = getAllHostAddress();
        for (InetAddress address : allInetAddresses) {
            if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                noLoopbackAddresses.add(address.getHostAddress());
            }
        }
        return noLoopbackAddresses;
    }

    public static String getFirstNoLoopbackAddress() {
        Collection<String> allNoLoopbackAddresses = getAllIpv4NoLoopbackAddresses();
        Assert.isTrue(!allNoLoopbackAddresses.isEmpty(), " Sorry, seems you don't have a network card!");
        return allNoLoopbackAddresses.iterator().next();
    }

    public static String getLocalHostName() {
        try {
            InetAddress netAddress = InetAddress.getLocalHost();
            return netAddress.getHostName();
        } catch (UnknownHostException e) {
            return "";
        }
    }

    public static String getLocalIp() {
        try {
            InetAddress netAddress = InetAddress.getLocalHost();
            return netAddress.getHostAddress();
        } catch (UnknownHostException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(getAllIpv4NoLoopbackAddresses());
        System.out.println(getLocalHostName());
    }
}

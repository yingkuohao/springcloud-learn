package com.alicp.account.cuntao.tool;

import ch.ethz.ssh2.Connection;
import com.taobao.tail.logcore.RemoteShellTool;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/13
 * Time: 下午5:20
 * CopyRight: taobao
 * Descrption:
 */

public class RemoteShellTollTest {
    @Test
    public void testMain() {

        RemoteShellTool remoteShellTool1 = new RemoteShellTool();
        //        String ipAddr = "10.1.6.202";
        String ipAddr = "101.201.233.247";
        remoteShellTool1.setIpAddr(ipAddr);
        try {
            //            boolean isLogin = remoteShellTool1.loginByKeygen("uuzz1", "/Users/chengjing/.ssh/id_rsa", "kuohao");
            Connection isLogin = remoteShellTool1.loginByKeygen("root", "/Users/chengjing/.ssh/id_rsa", "kuohao");
            System.out.println("islogin=" + isLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RemoteShellTool remoteShellTool = new RemoteShellTool("101.201.233.247", "root", "Lottery-2016", "UTF-8");
        String ls = "ls -al /root/alicpjkc/logs";
        String s = remoteShellTool.exec(ls);
        System.out.println("s-=" + s);
        String cmd = "tail -f /root/alicpjkc/logs/jkc-crm.log";
        remoteShellTool.execTail(cmd, null);
    }
}
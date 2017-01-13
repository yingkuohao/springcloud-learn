package com.taobao.tail.logcore;

import java.io.*;
import java.nio.charset.Charset;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.taobao.tail.config.LogConfig;
import com.taobao.tail.consts.LogConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/13
 * Time: 上午9:41
 * CopyRight: taobao
 * Descrption:
 */

public class RemoteShellTool {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Connection conn;
    private String ipAddr;
    private String charset = Charset.defaultCharset().toString();
    private String userName;
    private String password;

    public RemoteShellTool(String ipAddr, String userName, String password, String charset) {
        this.ipAddr = ipAddr;
        this.userName = userName;
        this.password = password;
        if (charset != null) {
            this.charset = charset;
        }
    }

    public RemoteShellTool(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public RemoteShellTool() {
    }

    public static Connection getConn(String ipAddr, LogConfig logConfig) throws IOException {
        RemoteShellTool remoteShellTool = new RemoteShellTool(ipAddr);
        return remoteShellTool.loginByKeygen(logConfig.getUserName(), logConfig.getKeyGenPath(), logConfig.getPwd());
    }

    /**
     * @param ipAddr
     * @param keyGenPath
     * @param userName
     * @param pwd
     * @return
     * @throws IOException
     */
    public static Connection getConn(String ipAddr, String keyGenPath, String userName, String pwd) throws IOException {
        RemoteShellTool remoteShellTool = new RemoteShellTool(ipAddr);
        return remoteShellTool.loginByKeygen(userName, keyGenPath, pwd);
    }


    /**
     * 登录远程Linux主机
     *
     * @return
     * @throws IOException
     */
    public boolean login() throws IOException {
        conn = new Connection(ipAddr);
        conn.connect(); // 连接
        return conn.authenticateWithPassword(userName, password); // 认证
    }

    /**
     * 登录远程Linux主机
     *
     * @return
     * @throws IOException
     */
    public Connection loginByKeygen(String userName, String keyGenPath, String pwd) throws IOException {
        conn = new Connection(ipAddr);
        conn.connect(); // 连接
        File file = new File(keyGenPath);
        boolean isloged = conn.authenticateWithPublicKey(userName, file, pwd);
        return isloged ? conn : null;
    }

    /**
     * 执行Shell脚本或命令
     *
     * @param cmds 命令行序列
     * @return
     */
    public void exec1(String cmds, WebSocketSession webSocketSession) {
        InputStream in = null;
        String result = "";
        try {
            if (this.login()) {
                TailfTask tailfTask = new TailfTask(cmds, conn, webSocketSession);
                Thread thread = new Thread(tailfTask);
                thread.start();  //yingkhtodo:desc:线程如何关闭,在websocket close的时候关闭
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }


    /**
     * 执行Shell脚本或命令
     *
     * @param cmds 命令行序列
     * @return
     */
    public String exec(String cmds) {
        InputStream in = null;
        String result = "";
        try {
            if (this.login()) {
                Session session = conn.openSession(); // 打开一个会话
                session.execCommand(cmds);
                in = session.getStdout();
                String logs = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
//                    logger.info("line=" + line);
                    logs += (line + LogConsts.prefix);
                }
                logger.info("logs=" + logs);
                result = logs;
//                result = this.processStdout(in, this.charset);
                conn.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    /**
     * 解析流获取字符串信息
     *
     * @param in      输入流对象
     * @param charset 字符集
     * @return
     */
    public String processStdout(InputStream in, String charset) {
        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while (in.read(buf) != -1) {
                sb.append(new String(buf, charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public static void main(String[] args) {
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
        remoteShellTool.exec1(cmd, null);
    }
}

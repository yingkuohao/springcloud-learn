package com.taobao.tail.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.taobao.tail.consts.LogConsts;
import com.taobao.tail.logcore.TailfTask;
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
                TailfTask tailfTask = new TailfTask(cmds, conn,webSocketSession);
                Thread thread = new Thread(tailfTask);
                thread.start();
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
                    logger.info("line=" + line);
                    logs += (line + LogConsts.prefix);
                }
                logger.info("logs=" + logs);
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

    public static void main(String[] args) {
        RemoteShellTool remoteShellTool = new RemoteShellTool("101.201.233.247", "root", "Lottery-2016", "UTF-8");
        String cmd = "tail -f /root/alicpjkc/logs/jkc-crm.log";
//        String s = remoteShellTool.exec(cmd);
//        System.out.println("s-=" + s);
     remoteShellTool.exec1(cmd,null);
    }
}

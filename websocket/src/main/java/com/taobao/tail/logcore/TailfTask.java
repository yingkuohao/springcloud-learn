package com.taobao.tail.logcore;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/13
 * Time: 上午10:07
 * CopyRight: taobao
 * Descrption:
 */

public class TailfTask implements Runnable {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    String logPath;
    private Connection conn;
    private WebSocketSession webSocketSession;

    public TailfTask(String logPath, Connection conn, WebSocketSession webSocketSession) {
        this.logPath = logPath;
        this.conn = conn;
        this.webSocketSession = webSocketSession;
    }

    @Override
    public void run() {
        String line;
        InputStream in = null;
        logger.info("logtail run,logPath={}", logPath);
        try {
            // 执行tail -f命令
            String cmd = "tail -f " + logPath;
            Session session = conn.openSession(); // 打开一个会话
            session.execCommand(cmd);
            in = session.getStdout();
            String logs = "";
//            Process process = Runtime.getRuntime().exec("tail -f " + logPath);
//            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                logger.info("line=" + line);
                if (webSocketSession != null) {
                    webSocketSession.sendMessage(new TextMessage(line + "<br>"));
                }
            }
        } catch (IOException e) {
            logger.error("tail log error,e={}", e);
        }
    }
}

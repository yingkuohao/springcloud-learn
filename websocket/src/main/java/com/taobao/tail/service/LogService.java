package com.taobao.tail.service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.alibaba.fastjson.JSONObject;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.sftp.SftpFile;
import com.taobao.tail.config.LogConfig;
import com.taobao.tail.consts.LogConsts;
import com.taobao.tail.consts.LogVO;
import com.taobao.tail.consts.ServerVO;
import com.taobao.tail.logcore.RemoteShellTool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/11
 * Time: 下午4:50
 * CopyRight: taobao
 * Descrption:
 */

@Component
public class LogService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    LogConfig logConfig;

    private Map<String, List<ServerVO>> appMap = new HashMap<String, List<ServerVO>>();

    private Map<String, ServerVO> serverMap = new HashMap<String, ServerVO>();

    @PostConstruct
    public void init() {
        String ip = "101.201.233.247";
        ServerVO serverVO = new ServerVO(ip, "root", "Lottery-2016");
        serverMap.put(ip, serverVO);

        String appName = "jkc-crm";
        List<ServerVO> serverVOList = new ArrayList<ServerVO>();
        serverVOList.add(serverVO);
        appMap.put(appName, serverVOList);
    }

    public Map<String, List<ServerVO>> getAppMap() {
        return appMap;
    }

    public Map<String, ServerVO> getServerMap() {
        return serverMap;
    }


    /**
     * 根据目标机器的ip和日志地址,获取下边的所有文件
     * 通过keygen的形式
     *
     * @param ip
     * @param path
     * @return
     */
    public String getAllLogFiles(String ip, String path) {
        Connection conn = null;
        InputStream in = null;
        String result = "";
        if (StringUtils.isBlank(path)) {
            logger.error("path is null!");
            return null;
        }
        try {
            conn = RemoteShellTool.getConn(ip, logConfig);
            if (conn != null) {
                Session session = conn.openSession(); // 打开一个会话
                String cmds = "ls -l " + path;
                session.execCommand(cmds);
                in = session.getStdout();
                String logs = "";
                List<LogVO> list = new ArrayList<LogVO>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                int i = 1;
                while ((line = reader.readLine()) != null) {
                    // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
//                    logger.info("line=" + line);
                    LogVO logVO = new LogVO();
                    logVO.setId(i++);
                    String name = line.substring(line.lastIndexOf(" ") + 1, line.length()).trim();
                    logVO.setName(name);
                    if (line.startsWith("d")) {
                        logVO.setFile(false);
                        logVO.setIsParent(true);
                    } else {
                        logVO.setFile(true);
                        logVO.setIsParent(false);
                    }
                    logVO.setWholePath(path + "/" + name.trim());
                    list.add(logVO);
                }
                String logDirs = JSONObject.toJSONString(list);
                logger.info("jsonArray={}", logDirs);
                result = logDirs;
//                result = this.processStdout(in, this.charset);
                conn.close();
            }
        } catch (IOException e1) {
            logger.error("get files error", e1);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                logger.error("conn close error", e);
            }
        }
        return result;
    }


    /**
     * 获取本地的日志,
     *
     * @param logBaseDir
     * @return
     */
    public String getLogsLocal(String logBaseDir) {
        String logs = "";
        InputStream inputStream = null;
        try {
//            String s = "/Users/chengjing/alicpaccount/logs";
            if (StringUtils.isBlank(logBaseDir)) {
                logger.error("log dir is blank!");
                return null;
            }

            Process process = Runtime.getRuntime().exec("ls " + logBaseDir);
            inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                logger.info("line=" + line);
                logs += (line + LogConsts.prefix);
            }
        } catch (Exception e) {
            logger.error("getLogsLocal errror", e);
        } finally {
            try {
                logger.info("logls onclose");
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception e) {
                logger.error("inputStream close error", e);
            }
        }
        return logs;
    }


    /**
     * 遍历一个本地目录,汇总子目录
     * @param baseDir
     * @return
     */
    public String getChildFileLocal(String baseDir) {
        try {
            List<LogVO> list = new ArrayList<LogVO>();
            final int[] i = {1};
            Files.list(Paths.get(baseDir)).forEach(n -> {
                try {
                    if (!Files.isHidden(n)) {
                        LogVO logVO = new LogVO();
                        logVO.setId(i[0]++);
                        String name = n.getFileName().toString();
                        logVO.setName(name);
                        logVO.setFile(n.toFile().isFile());
                        logVO.setIsParent(n.toFile().isDirectory());
                        logVO.setWholePath(baseDir + "/" + name);
                        list.add(logVO);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            String logDirs = JSONObject.toJSONString(list);
            logger.info("jsonArray={}", logDirs);
            return logDirs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



}

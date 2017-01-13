package com.taobao.tail.service;

import com.alibaba.fastjson.JSONObject;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.sftp.SftpFile;
import com.taobao.tail.consts.LogVO;
import com.taobao.tail.consts.ServerVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/13
 * Time: 下午5:06
 * CopyRight: taobao
 * Descrption:
 */

public class SShService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 通过ssh 连接远程服务器
     * 用户名密码的形式
     *
     * @param ip
     * @param user
     * @param password
     * @param logBaseDir
     * @return
     */
    public String getSShLogs(String ip, String user, String password, String logBaseDir) {
        SshClient client = new SshClient();
        try {
            client.connect(ip);
            //设置用户名和密码
            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername(user);
            pwd.setPassword(password);
            int result = client.authenticate(pwd);
            if (result == AuthenticationProtocolState.COMPLETE) {//如果连接完成
                System.out.println("===============" + result);
                List<SftpFile> list = client.openSftpClient().ls(logBaseDir);
                int i = 0;
                List<LogVO> logDetails = new ArrayList<LogVO>();
                for (SftpFile f : list) {
                    LogVO logVO = new LogVO();
                    logVO.setId(i++);
                    String name = f.getFilename();
                    logVO.setName(name);
                    logVO.setFile(f.isFile());
                    logVO.setIsParent(f.isDirectory());
                    logVO.setWholePath(logBaseDir + "/" + name);
                    logDetails.add(logVO);
                }
                String logDirs = JSONObject.toJSONString(logDetails);
                logger.info("logDetails={}", logDetails);
                return logDirs;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    SshClient client = new SshClient();

    @Deprecated
    //测试,铜鼓用户名密码连接服务器并读取文件
    public void connectToServer() {
        try {
            client.connect("101.201.233.247");
            //设置用户名和密码
            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername("root");
            pwd.setPassword("Lottery-2016");
            int result = client.authenticate(pwd);
            if (result == AuthenticationProtocolState.COMPLETE) {//如果连接完成
                System.out.println("===============" + result);
                List<SftpFile> list = client.openSftpClient().ls("/home/admin/logs/");
                client.openSessionChannel().executeCommand("tail -f /Users/chengjing/alicpaccount/logs/alicp-account-cuntao.log");

                for (SftpFile f : list) {
                    System.out.println(f.getFilename());
                    System.out.println(f.getAbsolutePath());
                    if (f.getFilename().equals("aliases")) {
                        OutputStream os = new FileOutputStream("d:/mail/" + f.getFilename());
                        client.openSftpClient().get("/etc/mail/aliases", os);
                        //以行为单位读取文件start
                        File file = new File("d:/mail/aliases");
                        BufferedReader reader = null;
                        try {
                            System.out.println("以行为单位读取文件内容，一次读一整行：");
                            reader = new BufferedReader(new FileReader(file));
                            String tempString = null;
                            int line = 1;//行号
                            //一次读入一行，直到读入null为文件结束
                            while ((tempString = reader.readLine()) != null) {
                                //显示行号
                                System.out.println("line " + line + ": " + tempString);
                                line++;
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e1) {
                                }
                            }
                        }
                        //以行为单位读取文件end
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SShService sShService = new SShService();
        sShService.connectToServer();
    }
}

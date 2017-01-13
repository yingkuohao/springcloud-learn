package com.taobao.tail.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.tail.config.LogConfig;
import com.taobao.tail.consts.LogConsts;
import com.taobao.tail.consts.LogVO;
import com.taobao.tail.samples.websocket.echo.EchoLogLsHandler;
import com.taobao.tail.service.LogService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LogConfig logConfig;

    @RequestMapping("/log")
    public String log(Model model) {
        logger.info("log ok");
        return "logs/log";
    }


    @RequestMapping("/index")
    public String index(Model model) {
        logger.info("index ok");
        return "logs/index";
    }


    @RequestMapping("/logls")
    public String logls(Model model) {

//        String logBaseDir = "/Users/chengjing/alicpaccount/logs";

        String logBaseDir = logConfig.getLogBaseDir();
        logger.info("logls ok,logBaseDir={}", logBaseDir);

        model.addAttribute("logBaseDir", logBaseDir);
        return "logs/logls";
    }

    @RequestMapping("/page")
    public String page(Model model) {
        logger.info("page ok");
        return "logs/page";
    }

    @Autowired
    private LogService logService;

    /**
     * 异步获得资源（销售代表，门店，终端）
     *
     * @param id   资源ID
     * @param name log文件名称
     * @return
     */
    @RequestMapping(value = "/ajax/getCrResources", method = RequestMethod.POST)
    @ResponseBody
    public String getCrResource(Long id, String name, @RequestParam("logBaseDir") String logBaseDir, String wholePath) {
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isBlank(logBaseDir)) {
            logger.error("log dir is blank!");
            return jsonArray.toString();
        }

        if (wholePath != null) {
            return getChildFile(wholePath);
        }
        return getChildFile(logBaseDir);
        //yingkhtodo:desc:根据服务器ip和用户名密码,获取日志路径
    }

    public String getChildFile(String baseDir) {
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

    public static void main(String[] args) {
        String s = "/Users/chengjing/logs";
        try {
            Files.list(Paths.get(s))
                    .filter(Files::isDirectory)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogController logController = new LogController();
        String ss = logController.getChildFile(s);
        System.out.println("ss=" + ss);
    }


}

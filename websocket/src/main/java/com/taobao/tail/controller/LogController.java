package com.taobao.tail.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.tail.config.LogConfig;
import com.taobao.tail.consts.LogConsts;
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
    public String getCrResource(Long id, String name, @RequestParam("logBaseDir") String logBaseDir) {
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isBlank(logBaseDir)) {
            logger.error("log dir is blank!");
            return jsonArray.toString();
        }
        String logFiles = logService.getLogs(logBaseDir);
        if (logFiles == null) {
            logger.error("logFiles is empty,logBaseDir=", logBaseDir);
            return jsonArray.toString();
        }

        String[] loglsStrs = logFiles.split(LogConsts.prefix);
        for (int i = 0; i < loglsStrs.length; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i);
            jsonObject.put("name", loglsStrs[i]);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
}

package com.taobao.tail.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/log/")
public class LogController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


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
        logger.info("logls ok");
        return "logs/logls";
    }

    @Autowired
    private LogService logService;

    /**
     * 异步获得资源（销售代表，门店，终端）
     *
     * @param id   资源ID
     * @param type 0为根结点，1为销售代表，2为门店，3为终端
     * @return
     */
    @RequestMapping(value = "/ajax/getCrResources", method = RequestMethod.POST)
    @ResponseBody
    public String getCrResource(Long id, Integer type) {
        String s = logService.getLogs();
        String[] loglsStrs = s.split(LogConsts.prefix);
        JSONArray jsonArray=new JSONArray();
        for (int i = 0; i < loglsStrs.length; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i);
            jsonObject.put("name", loglsStrs[i]);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
}

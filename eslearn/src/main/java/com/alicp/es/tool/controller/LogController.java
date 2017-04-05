package com.alicp.es.tool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alicp.es.tool.service.parser.dao.mapper.LogAgentConfigMapper;
import com.alicp.es.tool.service.parser.dao.mapper.LogPathConfigMapper;
import com.alicp.es.tool.service.parser.dao.model.LogAgentConfigDO;
import com.alicp.es.tool.service.parser.dao.model.LogPathConfigDO;
import com.google.common.collect.Lists;
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

import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LogAgentConfigMapper agentConfigMapper;
    @Autowired
    LogPathConfigMapper logPathConfigMapper;

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

        String logBaseDir = "test.log";
        logger.info("logls ok,logBaseDir={}", logBaseDir);

        model.addAttribute("logBaseDir", logBaseDir);
        return "logs/logls";
    }

    @RequestMapping("/page")
    public String page(Model model) {
        logger.info("page ok");
        return "logs/page";
    }


    /**
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
            logBaseDir = wholePath;
        }
        String ip = "101.201.233.247";         //yingkhtodo:desc:这里是写死的ip,需要动态
        return "";
        //yingkhtodo:desc:根据服务器ip和用户名密码,获取日志路径
    }

    @RequestMapping("/agent/query")
    public String getAgentInfo(Model model, @RequestParam("bizName") String bizName, @RequestParam("appName") String appName) {
        if (StringUtils.isEmpty(bizName) || StringUtils.isEmpty(appName)) {
            logger.info("param empty! ");
            model.addAttribute("tipMsg", "查询数据不存在");
        } else {
            LogAgentConfigDO logAgentConfigDO = agentConfigMapper.getAgentByApp(bizName, appName);
            model.addAttribute("ssdList", Lists.newArrayList(logAgentConfigDO));
        }
        return pageView;
    }

    private static final String pageView = "logs/agentinfo";
    @RequestMapping("/agentinfo")
    public String appinfo(Model model) {
        logger.info("log ok");
        return pageView;
    }

    @RequestMapping("/config/query")
    public String getLogConfigInfo(Model model, @RequestParam("agentId") Integer agentId) {
        if ( null == agentId) {
            logger.info("param empty! ");
            model.addAttribute("tipMsg", "查询数据不存在");
        } else {
            List<LogPathConfigDO> logPathConfigDOList = logPathConfigMapper.getLogPathByAgentId(agentId);
            model.addAttribute("ssdList", logPathConfigDOList);
        }
        return  "logs/logconfig";
    }
}

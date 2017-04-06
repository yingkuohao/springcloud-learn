package com.alicp.es.tool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alicp.es.tool.service.ESConfig;
import com.alicp.es.tool.service.parser.FileReadUtil;
import com.alicp.es.tool.service.parser.dao.mapper.LogAgentConfigMapper;
import com.alicp.es.tool.service.parser.dao.mapper.LogPathConfigMapper;
import com.alicp.es.tool.service.parser.dao.model.LogAgentConfigDO;
import com.alicp.es.tool.service.parser.dao.model.LogPathConfigDO;
import com.alicp.middleware.log.AlicpLog;
import com.alicp.middleware.log.BizLog;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LogAgentConfigMapper agentConfigMapper;
    @Autowired
    LogPathConfigMapper logPathConfigMapper;
    @Autowired
    ESConfig esConfig;

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


    //代理端信息主页
    @RequestMapping("/agentinfo")
    public String appinfo(Model model) {
        logger.info("log ok");
        return pageView;
    }

    //代理端信息查询
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

    //代理端信息新增
    @RequestMapping("/agent/addAgent")
    public String addAgent(Model model) {
        return "logs/agentAdd";
    }

    //代理端信息保存
    @RequestMapping("/agent/save")
    public String agentSave(Model model, @RequestParam("bizName") String bizName, @RequestParam("appName") String appName, @RequestParam("ips") String ips) {
        if (StringUtils.isEmpty(bizName) || StringUtils.isEmpty(appName) || StringUtils.isEmpty(ips)) {
            logger.info("param empty! ");
            model.addAttribute("tipMsg", "查询数据不存在");
        } else {
            LogAgentConfigDO logAgentConfigDO = new LogAgentConfigDO();
            logAgentConfigDO.setBizName(bizName);
            logAgentConfigDO.setAppName(appName);
            logAgentConfigDO.setIps(ips);
            int agentId = agentConfigMapper.insert(logAgentConfigDO);
//            model.addAttribute("ssdList", Lists.newArrayList(logAgentConfigDO));
            if (agentId > 0) {
                model.addAttribute("tipMsg", "操作成功！");
            } else {
                model.addAttribute("tipMsg", "操作失败！请查看日志!");
            }
            return getAgentInfo(model, bizName, appName);
        }
        return pageView;
    }

    private static final String pageView = "logs/agentinfo";

    //配置信息查询
    @RequestMapping("/config/query")
    public String getLogConfigInfo(Model model, @RequestParam("agentId") Integer agentId) {
        if (null == agentId) {
            logger.info("param empty! ");
            model.addAttribute("tipMsg", "查询数据不存在");
        } else {
            List<LogPathConfigDO> logPathConfigDOList = logPathConfigMapper.getLogPathByAgentId(agentId);
            model.addAttribute("ssdList", logPathConfigDOList);
        }
        return "logs/logconfig";
    }

    //配置信息新增
    @RequestMapping("/config/addConfig")
    public String addConfig(Model model, @RequestParam(name = "agentId") Integer agentId) {
        model.addAttribute("agentId", agentId);
        return "logs/configAdd";
    }

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/config/save")
    public String uploadTransportDoc(@RequestParam("myfile") MultipartFile myfile, @RequestParam("agentId") int agentId,
                                     @RequestParam("pattern") String pattern, @RequestParam("inputPath") String inputPath,
                                     Model model) {
        model.addAttribute("tipMsg", "脚本文件导入失败");
        if (!myfile.isEmpty()) {
            try {
                String fileSplit = "/";
                long currtime = System.currentTimeMillis();
                LogAgentConfigDO logAgentConfigDO = agentConfigMapper.getLogAgentConfigDO(agentId);
                StringBuffer stringBuffer = new StringBuffer(esConfig.getScriptBasePath());
                stringBuffer.append(fileSplit).append(logAgentConfigDO.getBizName()).append(fileSplit).append(logAgentConfigDO.getAppName());
                String outPath = stringBuffer.append(fileSplit).append(myfile.getOriginalFilename()).toString();

                logger.info("script out put path={}", outPath);
                createFile(myfile, outPath);

                LogPathConfigDO logPathConfigDO = new LogPathConfigDO();
                logPathConfigDO.setAgentId(agentId);
                logPathConfigDO.setPattern(pattern);
                logPathConfigDO.setInputPath(inputPath);
                logPathConfigDO.setScriptPath(outPath);
                int i = logPathConfigMapper.insert(logPathConfigDO);
                if (i > 0) {
                    model.addAttribute("tipMsg", "脚本文件导入成功");
                }
                logger.info("import use time {}", System.currentTimeMillis() - currtime);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return getLogConfigInfo(model, agentId);
    }

    private void createFile(@RequestParam("myfile") MultipartFile myfile, String outPath) throws IOException {
        File file = new File(outPath);
        if (file.exists()) {
            file.delete();
            logger.info("file exist,delete ok!path={}", outPath);
        }
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                logger.error("create new file error,outPath=" + outPath);
            }
            logger.info("file dir not exist,crate parentDir ok!,outPath={}", outPath);
        }
        Files.write(Paths.get(outPath), myfile.getBytes(), StandardOpenOption.CREATE);
    }
}

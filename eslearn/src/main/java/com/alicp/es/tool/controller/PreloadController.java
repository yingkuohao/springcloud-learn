package com.alicp.es.tool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreloadController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/health")
    public String health() {
        logger.info("health ok");
        return "OK";
    }

    @RequestMapping("/checkpreload.htm")
    public String checkpreload() {
        logger.info("checkpreload ok");
        return "success";
    }

}

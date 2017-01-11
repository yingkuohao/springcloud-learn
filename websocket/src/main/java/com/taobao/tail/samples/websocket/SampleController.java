package com.taobao.tail.samples.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/11
 * Time: 上午11:06
 * CopyRight: taobao
 * Descrption:
 */
@Controller()
@RequestMapping("/samples")
public class SampleController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/index")
    public String index(Model model) {
        logger.info("index ok");
        return "samples/index";
    }

    @RequestMapping("/echo")
    public String echo(Model model) {
        logger.info("echo ok");
        return "samples/echo";
    }
    @RequestMapping("/snake")
    public String snake(Model model) {
        logger.info("snake ok");
        return "samples/snake";
    }
}

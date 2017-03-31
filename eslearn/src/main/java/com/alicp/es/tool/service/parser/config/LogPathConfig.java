package com.alicp.es.tool.service.parser.config;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/31
 * Time: 上午11:14
 * CopyRight: taobao
 * Descrption:
 */

public class LogPathConfig {
    private String inputPath;   //日志的路径,含名称
    private String pattern; //日志的后缀格式,如yyyy-MM-dd
    private String scriptPath; //脚本路径
    private String outPutName; //输出名字

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public String getOutPutName() {
        return outPutName;
    }

    public void setOutPutName(String outPutName) {
        this.outPutName = outPutName;
    }
}

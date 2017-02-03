package com.alicp.logapp.log.api;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/25
 * Time: 下午3:58
 * CopyRight: taobao
 * Descrption:
 */

public class AlicpLog {

    public static Logger jvm;
    public static Logger biz;

    static {
        jvm = getJVMLogger();
        biz = getBizLogger();
    }

    /**
     * 记录了当前 EagleEye 所加载的位置
     *
     * @since 1.3.4
     */
    public static final String CLASS_LOCATION = getEagleEyeLocation();

    /**
     * 用户目录
     */
    static final String USER_HOME = locateUserHome();

    /**
     * 基础日志默认放置的位置：~/logs/
     */
    static final String BASE_LOG_DIR = locateBaseLogPath();

    /**
     * EagleEye 日志默认放置的位置：~/logs/alicplog/
     */
    static final String ALICP_LOG_DIR = locateAlicpLogPath();

    /**
     * 应用日志默认放置的位置：~/${project.name}/logs/
     */
    static final String APP_LOG_DIR = locateAppLogPath();

    /**
     * 强制指定写日志用的编码
     */
    static final Charset DEFAULT_CHARSET = getDefaultOutputCharset();

    /**
     * EagleEye RPC 日志文件名
     */
    static final String ALICP_JVM_LOG_FILE = AlicpLog.ALICP_LOG_DIR + "jvm_monitor.log";
    /**
     * EagleEye BIZ 日志文件名
     */
    static final String ALICP_BIZ_LOG_FILE = AlicpLog.ALICP_LOG_DIR + "biz_alicp.log";
    /**
     * static final long MAX_SELF_LOG_FILE_SIZE = 200 * 1024 * 1024; // 200MB
     * static final long MAX_RPC_LOG_FILE_SIZE = 300 * 1024 * 1024; // 300MB
     * static final long MAX_BIZ_LOG_FILE_SIZE = 300 * 1024 * 1024; // 300MB
     * <p/>
     * /**
     * 业务某些字段可接受最大长度
     */
    static public final int MAX_BIZ_LOG_SIZE = 4 * 1024;

    /**
     * index 可接受字符串最大长度
     */
    static public final int MAX_INDEX_SIZE = 512;

    /**
     * userData 可接受单个数据的最大长度
     */
    static public final int MAX_USER_DATA_ENTRY_SIZE = 512;

    /**
     * userData 可接受全部数据的最大长度
     */
    static public final int MAX_USER_DATA_TOTAL_SIZE = 1024;


    /**
     * 返回 EagleEye class 的加载位置
     *
     * @return
     */
    static final String getEagleEyeLocation() {
        try {
            URL resource = AlicpLog.class.getProtectionDomain().getCodeSource().getLocation();
            if (resource != null) {
                return resource.toString();
            }
        } catch (Throwable t) {
            // ignore
        }
        return "unknown location";
    }

    /**
     * 根据系统参数，获取用户目录，获取失败时返回 /tmp/
     *
     * @return 返回路径，结尾包含“/”
     * @since 1.3.0
     */
    static private final String locateUserHome() {
        String userHome = LogCoreUtils.getSystemProperty("user.home");
        if (LogCoreUtils.isNotBlank(userHome)) {
            if (!userHome.endsWith(File.separator)) {
                userHome += File.separator;
            }
        } else {
            userHome = "/tmp/";
        }
        return userHome;
    }

    /**
     * 根据系统参数，设置基础的日志目录。判断优先级：
     * <ol>
     * <li>如果设置了 ${JM.LOG.PATH}，在 ${JM.LOG.PATH}/ 下面输出日志。
     * <li>在 ${user.home}/logs/ 下面输出日志。
     * </ol>
     *
     * @return 返回路径，结尾包含“/”
     * @since 1.3.0
     */
    static private final String locateBaseLogPath() {
        String tmpPath = LogCoreUtils.getSystemProperty("JM.LOG.PATH");
        if (LogCoreUtils.isNotBlank(tmpPath)) {
            if (!tmpPath.endsWith(File.separator)) {
                tmpPath += File.separator;
            }
        } else {
            tmpPath = USER_HOME + "logs" + File.separator;
        }
        return tmpPath;
    }

    /**
     * 根据系统参数，设置 EagleEye 的日志目录。判断优先级：
     * <ol>
     * <li>如果设置了 ${EAGLEEYE.LOG.PATH}，在 ${EAGLEEYE.LOG.PATH}/ 下面输出日志。
     * <li>在 ${BASE_LOG_DIR}/eagleeye/ 下面输出日志。
     * </ol>
     *
     * @return 返回路径，结尾包含“/”
     * @since 1.2.9
     */
    static private final String locateAlicpLogPath() {
        String tmpPath = LogCoreUtils.getSystemProperty("EAGLEEYE.LOG.PATH");
        if (LogCoreUtils.isNotBlank(tmpPath)) {
            if (!tmpPath.endsWith(File.separator)) {
                tmpPath += File.separator;
            }
        } else {
            tmpPath = BASE_LOG_DIR + "alicplog" + File.separator;
        }
        return tmpPath;
    }

    /**
     * 根据系统参数，设置 EagleEye 的日志目录。判断优先级：
     * <ol>
     * <li>如果设置了 ${project.name}，在 ${user.home}/${project.name}/logs/ 下面输出日志。
     * <li>在 ${EAGLEEYE_LOG_DIR}/ 下面输出日志。
     * </ol>
     *
     * @return 返回路径，结尾包含“/”
     * @since 1.3.0
     */
    static private final String locateAppLogPath() {
        String appName = LogCoreUtils.getSystemProperty("project.name");
        if (LogCoreUtils.isNotBlank(appName)) {
            return USER_HOME + appName + File.separator + "logs" + File.separator;
        } else {
            return ALICP_LOG_DIR;
        }
    }

    /**
     * 返回 EagleEye 输出日志的字符编码，默认从 ${EAGLEEYE.CHARSET} 加载，
     * 如果未设置，按照 GB18030、GBK、UTF-8 的顺序依次尝试。
     *
     * @return
     * @since 1.3.0
     */
    static final Charset getDefaultOutputCharset() {
        Charset cs;
        String charsetName = LogCoreUtils.getSystemProperty("EAGLEEYE.CHARSET");
        if (LogCoreUtils.isNotBlank(charsetName)) {
            charsetName = charsetName.trim();
            try {
                cs = Charset.forName(charsetName);
                if (cs != null) {
                    return cs;
                }
            } catch (Exception e) {
                // quietly
            }
        }
        try {
            cs = Charset.forName("GB18030");
        } catch (Exception e) {
            try {
                cs = Charset.forName("GBK");
            } catch (Exception e2) {
                cs = Charset.forName("UTF-8");
            }
        }
        return cs;
    }

    private static Logger getJVMLogger() {
        return getLogger("alicplog-jvm", ALICP_JVM_LOG_FILE);
    }

    private static Logger getBizLogger() {
        return getLogger("alicplog-biz", ALICP_BIZ_LOG_FILE);
    }


    private static Logger getLogger(String loggerName, String fileName) {

        Logger logger = LoggerFactory.getLogger(loggerName);
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        ch.qos.logback.classic.Logger newLogger = (ch.qos.logback.classic.Logger) logger;
        //Remove all previously added appenders from this logger instance.
        newLogger.detachAndStopAllAppenders();

        //define appender
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<ILoggingEvent>();

        //policy
        TimeBasedRollingPolicy<ILoggingEvent> policy = new TimeBasedRollingPolicy<ILoggingEvent>();
        policy.setContext(loggerContext);
        policy.setMaxHistory(10);
//        ${LOG_DIR}/jvm-monitor.log.%d{yyyy-MM-dd}
        policy.setFileNamePattern(fileName + ".%d{yyyy-MM-dd}");
        System.out.println(ALICP_BIZ_LOG_FILE);
        policy.setParent(appender);
        policy.start();

        //encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%message%n");
        encoder.start();

        //start appender
        appender.setRollingPolicy(policy);
        appender.setContext(loggerContext);
        appender.setEncoder(encoder);
        appender.setPrudent(true); //support that multiple JVMs can safely write to the same file.
        appender.start();

        newLogger.addAppender(appender);

        //setup level
        newLogger.setLevel(Level.INFO);
        //remove the appenders that inherited 'ROOT'.
        newLogger.setAdditive(false);
        return newLogger;
    }

    public static void main(String[] args) {
        AlicpLog alicpLog = new AlicpLog();

        Logger logger = alicpLog.getBizLogger();
        logger.info("testhaha ");
    }
}

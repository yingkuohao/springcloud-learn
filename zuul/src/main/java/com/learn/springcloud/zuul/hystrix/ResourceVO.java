package com.learn.springcloud.zuul.hystrix;

import com.learn.springcloud.zuul.consts.BaseDo;
import com.netflix.hystrix.util.LongAdder;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/8/23
 * Time: 下午4:15
 * CopyRight: taobao
 * Descrption:
 *
 * 这需要记录每一个时间点的访问量,因为用户请求是按时间点过来的
 */


public class ResourceVO extends BaseDo {
    private static final long serialVersionUID = 6888269499566162709L;
    private String bizId;
    private String urlPattern;               //urlpattern
    private Long limit = 5l;             //访问次数阈值
    private LongAdder currentViewCount=new LongAdder(); //当前访问次数
    private int time = 60;             //限定时间
    private String callback;         //被限制后的callback url

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public LongAdder getCurrentViewCount() {
        return currentViewCount;
    }

    public void setCurrentViewCount(LongAdder currentViewCount) {
        this.currentViewCount = currentViewCount;
    }

    public boolean outOfFlow() {
        return currentViewCount.longValue() >= limit.longValue();
    }

}

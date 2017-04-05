package com.alicp.es.tool.service.parser.dao.mapper;

import com.alicp.es.tool.service.parser.dao.model.LogAgentConfigDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/4/5
 * Time: 上午10:16
 * CopyRight: taobao
 * Descrption:
 */

public interface LogAgentConfigMapper {

    @Select("SELECT * FROM log_agent_config WHERE id = #{id}")
    LogAgentConfigDO getLogAgentConfigDO(@Param("id") Integer id);

    @Select("SELECT * FROM log_agent_config WHERE biz_name = #{bizName} AND app_name= #{appName}")
    LogAgentConfigDO getAgentByApp(String bizName, String appName);

    @Insert("insert into log_agent_config(id,biz_name,app_name,ips,gmt_create,gmt_modified)\n" +
            "\tvalues(#{id},#{bizName},#{appName},#{ips},now(),now())")
    int insert(LogAgentConfigDO LogAgentConfigDO);

    //    @Update("\tupdate log_agent_config\n" +
    //            "\t\tset store_id=#{storeId},payment_acconut=#{paymentAcconut},payment_acconut_quota=#{paymentAcconutQuota},open_account=#{openAccount},open_acount_quota=#{openAcountQuota},gmt_modified=now(),store_acount_number=#{storeAcountNumber},store_acount_password=#{storeAcountPassword},center_store_id=#{centerStoreId}\n" +
    //            "\t\twhere id = #{id} ")
//        void update(LogAgentConfigDO LogAgentConfigDO);

}

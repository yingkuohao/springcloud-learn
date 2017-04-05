package com.alicp.es.tool.service.parser.dao.mapper;

import com.alicp.es.tool.service.parser.dao.model.LogPathConfigDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/4/5
 * Time: 上午10:16
 * CopyRight: taobao
 * Descrption:
 */

public interface LogPathConfigMapper {

    @Select("SELECT * FROM log_path_config WHERE id = #{id}")
    LogPathConfigDO getLogPathById(@Param("id") Integer id);

    @Select("SELECT * FROM log_path_config WHERE agent_id = #{agentId} ")
    List<LogPathConfigDO> getLogPathByAgentId(Integer agentId );


    @Insert("insert into log_path_config(id,agent_id,input_path,pattern,script_path,gmt_create,gmt_modified)\n" +
            "\tvalues(#{id},#{agentId},#{inputPath}, #{pattern},#{scriptPath}, now(),now())")
    int insert(LogPathConfigDO logPathConfigDO);

    //    @Update("\tupdate log_path_config\n" +
    //            "\t\tset store_id=#{storeId},payment_acconut=#{paymentAcconut},payment_acconut_quota=#{paymentAcconutQuota},open_account=#{openAccount},open_acount_quota=#{openAcountQuota},gmt_modified=now(),store_acount_number=#{storeAcountNumber},store_acount_password=#{storeAcountPassword},center_store_id=#{centerStoreId}\n" +
    //            "\t\twhere id = #{id} ")
//        void update(LogPathConfigDO LogPathConfigDO);

}

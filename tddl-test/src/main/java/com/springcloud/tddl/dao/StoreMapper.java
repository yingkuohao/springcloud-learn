package com.springcloud.tddl.dao;

import com.springcloud.tddl.dao.model.StoreDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/9/6
 * Time: 下午5:46
 * CopyRight: taobao
 * Descrption:
 */

public interface StoreMapper {

    @Select("SELECT * FROM store WHERE id = #{id}")
    StoreDO getStore(@Param("id") Long id);

    @Select("SELECT * FROM store WHERE center_store_id = #{centerStoreId}")
      StoreDO getStoreByCenterStoreId(Integer centerStoreId);

    @Insert("insert into store(center_id,org_type,store_type,center_store_id,commission_rate,channel_id,channel_no,delete_flag,gmt_create,gmt_modified,center_name)\n" +
            "\tvalues(#{centerId},#{orgType},#{storeType},#{centerStoreId},#{commissionRate},#{channelId},#{channelNo},#{deleteFlag},now(),now(),#{centerName})")
    void insert(StoreDO storeDO);

    @Update("update store set center_id=#{centerId},org_type=#{orgType},store_type=#{storeType},center_store_id=#{centerStoreId},commission_rate=#{commissionRate},channel_id=#{channelId},channel_no=#{channelNo},delete_flag=#{deleteFlag},gmt_modified=now(),center_name=#{centerName}\n" +
            " where id = #{id} ")
    void update(Long id);
}

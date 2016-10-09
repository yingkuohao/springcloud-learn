package com.springcloud.tddl.dao;

import com.springcloud.tddl.dao.model.TerminalDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/9/6
 * Time: 下午5:46
 * CopyRight: taobao
 * Descrption:
 */

public interface TerminalMapper {

    @Select("SELECT * FROM terminal WHERE id = #{id}")
    TerminalDO getTerminalDO(@Param("id") Long id);

    @Select("SELECT * FROM terminal WHERE center_store_id = #{centerStoreId}")
    TerminalDO getTerminalByCenterStoreId(Integer centerStoreId);

    @Insert("insert into terminal(store_id,business_status,manage_status,center_terminal_no,gmt_create,gmt_modified,center_store_id,sn) " +
            " values(#{storeId},#{businessStatus},#{manageStatus},#{centerTerminalNo},now(),now(),#{centerStoreId},#{sn})")
    void insert(TerminalDO terminalDO);

    @Update("update terminal " +
            " set store_id=#{storeId},business_status=#{businessStatus},manage_status=#{manageStatus},center_terminal_no=#{centerTerminalNo},gmt_modified=now(),center_store_id=#{centerStoreId}\n" +
            " where id = #{id} ")
    void update(Long id);


    @Select("SELECT * FROM terminal")
    List<TerminalDO> getAllTerminalDO();

}

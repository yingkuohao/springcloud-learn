package com.springcloud.tddl.sharding.dao;

import com.springcloud.tddl.dao.model.StoreDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserTicketIndexDao {

    @Select("SELECT * FROM user_ticket_index WHERE user_id = #{userId}")
    public UserTicketIndex getUserTicketIndex(@Param("userId") Long userId);

}

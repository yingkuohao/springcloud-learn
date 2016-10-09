package com.springcloud.tddl.dao;

import com.springcloud.tddl.dao.model.StoreAccountDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/9/7
 * Time: 下午5:13
 * CopyRight: taobao
 * Descrption:
 */

public interface StoreAccountMapper {


    @Select("SELECT * FROM store_account WHERE id = #{id}")
    StoreAccountDO getStoreAccountDO(@Param("id") Long id);

    @Select("SELECT * FROM store_account WHERE center_store_id = #{centerStoreId}")
    StoreAccountDO getAccountByCenterStoreId(Integer centerStoreId);

      @Insert("insert into store_account(store_id,payment_acconut,payment_acconut_quota,open_account,open_acount_quota,gmt_create,gmt_modified,store_acount_number,store_acount_password,center_store_id)\n" +
              "\tvalues(#{storeId},#{paymentAcconut},#{paymentAcconutQuota},#{openAccount},#{openAcountQuota},now(),now(),#{storeAcountNumber},#{storeAcountPassword},#{centerStoreId})")
    void insert(StoreAccountDO storeAccountDO);

    @Update("\tupdate store_account\n" +
            "\t\tset store_id=#{storeId},payment_acconut=#{paymentAcconut},payment_acconut_quota=#{paymentAcconutQuota},open_account=#{openAccount},open_acount_quota=#{openAcountQuota},gmt_modified=now(),store_acount_number=#{storeAcountNumber},store_acount_password=#{storeAcountPassword},center_store_id=#{centerStoreId}\n" +
            "\t\twhere id = #{id} ")
    void update(Long id);
}

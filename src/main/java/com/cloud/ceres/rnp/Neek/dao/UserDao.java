package com.cloud.ceres.rnp.Neek.dao;

import com.cloud.ceres.rnp.Neek.dto.Order;
import com.cloud.ceres.rnp.Neek.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author heian
 * @create 2020-01-31-2:09 下午
 * @description
 */
@Mapper
public interface UserDao {

    //旧的查询
    @Select("SELECT o.id,o.custId,u.name FROM `user` u,`order` o WHERE u.custId=o.custId")
    List<Order> queryTotalInfo();

    //模拟  完成上述操作
    @Select("SELECT o.id,o.custId  FROM `order` o ")
    List<Order> queryOrderInfo();

    @Select("SELECT * FROM user u WHERE u.custId=#{custId}")
    User queryUserInfo(@Param("custId") int custId);



}

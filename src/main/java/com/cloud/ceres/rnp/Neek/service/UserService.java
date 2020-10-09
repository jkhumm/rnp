package com.cloud.ceres.rnp.Neek.service;

import com.cloud.ceres.rnp.Neek.annotation.NeedProxyEnhance;
import com.cloud.ceres.rnp.Neek.dto.Order;
import com.cloud.ceres.rnp.Neek.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author heian
 * @create 2020-02-02-12:45 上午
 * @description
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    //@Autowired
   // private StringRedisTemplate stringRedisTemplate;


    public List<Order>  queryTotalInfo(){
        List<Order> order = userDao.queryTotalInfo();
        System.out.println(order);
        return order;
    }

    @NeedProxyEnhance
    public List<Order> queryOrderInfo(){
        List<Order> order = userDao.queryOrderInfo();
        return order;
    }

 /*   @NeedPointcut
    public List<Order> queryTotalInfo2(){
        List<Order> order = userDao.queryTotalInfo();
        for (Order o : order) {
            if (StringUtils.isEmpty(o.getName())) {
                String custName = stringRedisTemplate.opsForValue().get(o.getCustId());
                if (!StringUtils.isEmpty(custName))
                    o.setName(custName);
            }
        }
        System.out.println(order);
        return order;
    }*/


}

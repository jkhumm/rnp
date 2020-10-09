package com.cloud.ceres.rnp.control.web;

import com.cloud.ceres.rnp.Neek.dto.Order;
import com.cloud.ceres.rnp.Neek.dao.UserDao;
import com.cloud.ceres.rnp.Neek.service.UserService;
import com.dn.study.girl.Girl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/test")
@RestController
public class Demo {

    @Autowired
    private Girl girl;

    @Autowired
    UserDao userDao;

    @GetMapping("test")
    public void fun(){
        System.out.println("注入的类" + girl);
    }


    @Autowired
    private UserService userService;



    @GetMapping("/queryOrder")
    public List<Order> queryOrder(){
        return userService.queryOrderInfo();
    }

   /* @GetMapping("/queryOrder")
    public List<Order> queryOrder2(){
        List<Order> orders = userService.queryOrderInfo();
        for (Order order : orders) {
            if (StringUtils.isEmpty(order.getName())){
                //先去查询缓存  这里的key就简单的以custId定义一下，实际生产请正确命名
                String custName = stringRedisTemplate.opsForValue().get(order.getCustId());
                if (StringUtils.isEmpty(custName)){
                    User user = userDao.queryUserInfo(order.getCustId());
                    order.setName(user.getName());
                    stringRedisTemplate.opsForValue().set(String.valueOf(order.getCustId()),user.getName());
                }
            }
        }
        return orders;
    }*/

}

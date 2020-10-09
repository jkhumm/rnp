package com.cloud.ceres.rnp.Neek.control;

import com.cloud.ceres.rnp.Neek.dto.Order;
import com.cloud.ceres.rnp.Neek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author heian
 * @create 2020-02-02-4:03 下午
 * @description
 */
@RestController
@RequestMapping("/neek")
public class DemoControl {

    @Autowired
    private UserService userService;

    @GetMapping("/queryOrder")
    public List<Order> queryOrder(){
        return userService.queryOrderInfo();
    }


}

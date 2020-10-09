package com.cloud.ceres.rnp.Neek.dto;


import com.cloud.ceres.rnp.Neek.annotation.NeedSetValue;
import com.cloud.ceres.rnp.Neek.dao.UserDao;

/**
 * @author heian
 * @create 2020-02-01-10:44 下午
 * @description
 */
public class Order {

    private int id;
    private int custId;
    //需要查询的信息
    //beenClass:反射需要知道的类
    //param:反射方法的入参 （因为我们要拿到方法，拿到方法除了知道方法名字外还需要知道方法的入参数类型）
    //method:反射执行所需的方法名
    //targetFiled：指的是dao层的返回user对象结果中的的所需字段名字，可以通过该名字拿到具体的值
    @NeedSetValue(beanClass = UserDao.class,param = "custId",method = "queryUserInfo",targetFiled = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", custId=" + custId +
                ", name='" + name + '\'' +
                '}';
    }
}

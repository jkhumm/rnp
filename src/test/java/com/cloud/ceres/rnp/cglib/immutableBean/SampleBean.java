package com.cloud.ceres.rnp.cglib.immutableBean;

import org.junit.Assert;
import org.springframework.cglib.beans.ImmutableBean;

public class SampleBean {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        SampleBean bean = new SampleBean();
        bean.setValue("Hello world");
        SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean); //创建不可变类
        Assert.assertEquals("Hello world",immutableBean.getValue());
        System.out.println(1);
        immutableBean.setValue("Hello cglib"); //直接修改将throw exception
    }
}

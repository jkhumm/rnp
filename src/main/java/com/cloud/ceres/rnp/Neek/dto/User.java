package com.cloud.ceres.rnp.Neek.dto;

/**
 * @author heian
 * @create 2020-02-02-12:23 上午
 * @description
 */
public class User {
    private int id;
    private String name;
    private int idCardNo;
    private int custId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(int idCardNo) {
        this.idCardNo = idCardNo;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

}

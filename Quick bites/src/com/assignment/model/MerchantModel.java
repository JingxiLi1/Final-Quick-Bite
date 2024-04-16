package com.assignment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户信息
 */
public class MerchantModel implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 商户编码
     */
    private String merchantNo;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户地址
     */
    private String merchantAdress;

    /**
     * 电话
     */
    private String phone;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 密码
     */
    private String password;

    private Date createTime;

    public MerchantModel(Integer id, String merchantNo, String merchantName, String merchantAdress, String phone, String contactPerson) {
        this.id = id;
        this.merchantNo = merchantNo;
        this.merchantName = merchantName;
        this.merchantAdress = merchantAdress;
        this.phone = phone;
        this.contactPerson = contactPerson;
    }

    public MerchantModel(Integer id, String merchantNo, String merchantName, String merchantAdress, String phone, String contactPerson, String password, Date createTime) {
        this.id = id;
        this.merchantNo = merchantNo;
        this.merchantName = merchantName;
        this.merchantAdress = merchantAdress;
        this.phone = phone;
        this.contactPerson = contactPerson;
        this.password = password;
        this.createTime = createTime;
    }

    public MerchantModel(String merchantNo, String merchantName, String merchantAdress, String phone, String contactPerson) {
        this.merchantNo = merchantNo;
        this.merchantName = merchantName;
        this.merchantAdress = merchantAdress;
        this.phone = phone;
        this.contactPerson = contactPerson;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAdress() {
        return merchantAdress;
    }

    public void setMerchantAdress(String merchantAdress) {
        this.merchantAdress = merchantAdress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

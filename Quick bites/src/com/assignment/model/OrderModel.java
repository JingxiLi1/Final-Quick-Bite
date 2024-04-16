package com.assignment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 */
public class OrderModel implements Serializable {

    private Integer id;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 订单编码
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 总金额
     */
    private Double totalAmount;

    /**
     * 状态 0:已下单,1:商家接单,2:配送中,3:完成
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 支付方式 0:微信,1:支付宝,2:现金
     */
    private Integer payChannel;

    /**
     * 支付方式描述
     */
    private String payChannelDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商户信息
     */
    private MerchantModel merchantModel;

    /**
     * 用户信息
     */
    private UserModel userModel;

    /**
     * 外卖人员ID
     */
    private Integer takeOutUserId;

    /**
     * 外卖员信息
     */
    private TakeOutUserModel takeOutUserModel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public MerchantModel getMerchantModel() {
        return merchantModel;
    }

    public void setMerchantModel(MerchantModel merchantModel) {
        this.merchantModel = merchantModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPayChannelDesc() {
        return payChannelDesc;
    }

    public void setPayChannelDesc(String payChannelDesc) {
        this.payChannelDesc = payChannelDesc;
    }

    public Integer getTakeOutUserId() {
        return takeOutUserId;
    }

    public void setTakeOutUserId(Integer takeOutUserId) {
        this.takeOutUserId = takeOutUserId;
    }

    public TakeOutUserModel getTakeOutUserModel() {
        return takeOutUserModel;
    }

    public void setTakeOutUserModel(TakeOutUserModel takeOutUserModel) {
        this.takeOutUserModel = takeOutUserModel;
    }
}

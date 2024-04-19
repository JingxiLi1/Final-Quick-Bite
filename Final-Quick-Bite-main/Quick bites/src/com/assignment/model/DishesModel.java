package com.assignment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜
 */
public class DishesModel implements Serializable {

    /**
     * 菜品ID
     */
    private Integer id;

    /**
     * 菜品编号
     */
    private String dishesNo;

    /**
     * 菜品名称
     */
    private String dishesName;

    /**
     * 价格
     */
    private Double price;

    /**
     * 商户编码
     */
    private Integer merchantId;

    private Date createTime;

    /**
     * 商户编码
     */
    private MerchantModel merchantModel;

    public DishesModel(Integer id, String dishesNo, String dishesName, Double price, Integer merchantId) {
        this.id = id;
        this.dishesNo = dishesNo;
        this.dishesName = dishesName;
        this.price = price;
        this.merchantId = merchantId;
    }

    public DishesModel(String dishesNo, String dishesName, Double price) {
        this.dishesNo = dishesNo;
        this.dishesName = dishesName;
        this.price = price;
    }

    public DishesModel(Integer id, String dishesName, Double price) {
        this.id = id;
        this.dishesName = dishesName;
        this.price = price;
    }

    public MerchantModel getMerchantModel() {
        return merchantModel;
    }

    public void setMerchantModel(MerchantModel merchantModel) {
        this.merchantModel = merchantModel;
    }

    public String getDishesNo() {
        return dishesNo;
    }

    public void setDishesNo(String dishesNo) {
        this.dishesNo = dishesNo;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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

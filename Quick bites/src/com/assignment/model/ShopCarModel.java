package com.assignment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车信息
 */
public class ShopCarModel implements Serializable {
    /**
     * 记录ID
     */
    private Integer id;

    /**
     * 记录ID
     */
    private Integer merchantId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 菜品ID
     */
    private Integer dishesId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 单价
     */
    private Double price;

    /**
     * 总价
     */
    private Double totalPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    private DishesModel dishesModel;


    public ShopCarModel(Integer userId, Integer dishesId, Integer count, Double price, Double totalPrice,Integer merchantId) {
        this.userId = userId;
        this.dishesId = dishesId;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.merchantId = merchantId;
    }

    public ShopCarModel(Integer id, Integer userId, Integer dishesId, Integer count, Double price, Double totalPrice, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.dishesId = dishesId;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDishesId() {
        return dishesId;
    }

    public void setDishesId(Integer dishesId) {
        this.dishesId = dishesId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public DishesModel getDishesModel() {
        return dishesModel;
    }

    public void setDishesModel(DishesModel dishesModel) {
        this.dishesModel = dishesModel;
    }

}

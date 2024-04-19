package com.assignment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单明细
 */
public class OrderDetailModel implements Serializable {
    private Integer id;

    /**
     * 订单ID
     */
    private Integer orderId;


    /**
     * 商品ID
     */
    private Integer dishesId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 金额
     */
    private Double price;

    /**
     * 总金额
     */
    private Double totalPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    private DishesModel dishesModel;

    public OrderDetailModel() {
    }

    public OrderDetailModel(Integer id, Integer orderId, Integer dishesId, Integer count, Double price, Double totalPrice, Date createTime) {
        this.id = id;
        this.orderId = orderId;
        this.dishesId = dishesId;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public DishesModel getDishesModel() {
        return dishesModel;
    }

    public void setDishesModel(DishesModel dishesModel) {
        this.dishesModel = dishesModel;
    }
}

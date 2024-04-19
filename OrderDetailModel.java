package Quickbites;

import java.io.Serializable;
import java.util.Date;

/**
 * Order Details
 */
public class OrderDetailModel implements Serializable {
    private Integer id;

    /**
     * Order ID

     */
    private Integer orderId;


    /**
     * DishID
     */
    private Integer dishesId;

    /**
     * count
     */
    private Integer count;

    /**
     * price
     */
    private Double price;

    /**
     * total price
     */
    private Double totalPrice;

    /**
     * createTime
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

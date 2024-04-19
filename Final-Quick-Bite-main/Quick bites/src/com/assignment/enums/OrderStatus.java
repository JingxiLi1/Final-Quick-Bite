package com.assignment.enums;

/**
 * orderstate
 */
public enum OrderStatus {
    ORDER_PLACED(0, "ordered"),
    MERCHANT_ACCEPTED(1, "restaurant accepted"),
    ON_DELIVERY(2, "Delivery in progress"),
    COMPLETED(3, "Accomplish");

    private final Integer statusCode;
    private final String desc;

    OrderStatus(Integer statusCode, String desc) {
        this.statusCode = statusCode;
        this.desc = desc;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getDesc() {
        return desc;
    }

    public static String getChannelCodyByCode(Integer code) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getStatusCode().equals(code)) {
                return orderStatus.getDesc();
            }
        }
        return null;
    }
}

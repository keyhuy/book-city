package com.key.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单实体类
 *
 * @author Key
 * @date 2021/09/23/21:41
 **/
public class Order {
    private String orderId;
    private Timestamp orderTime;
    private BigDecimal orderPrice;
    private Integer orderStatus;
    private Integer userId;

    public Order() {
    }

    public Order(String orderId, Timestamp orderTime, BigDecimal orderPrice, Integer orderStatus, Integer userId) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderTime=" + orderTime +
                ", orderPrice=" + orderPrice +
                ", orderStatus=" + orderStatus +
                ", userId=" + userId +
                '}';
    }
}

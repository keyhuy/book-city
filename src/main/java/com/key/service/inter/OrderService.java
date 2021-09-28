package com.key.service.inter;

import com.key.entity.Cart;
import com.key.entity.Order;
import com.key.entity.OrderItem;

import java.util.List;

/**
 * 订单模块service接口
 *
 * @author Key
 * @date 2021/09/23/22:03
 **/
public interface OrderService {

    /**
     * 根据购物车信息和用户id生成订单
     * @param cart 购物车对象
     * @param userId 用户id
     * @return 返回订单号
     */
    String createOrder(Cart cart, int userId);

    /**
     * 查看我的订单
     * @param userId 用户id
     * @return 返回对应id的订单集合
     */
    List<Order> checkMyOrders(int userId);

    /**
     * 签收订单
     * @param orderId 订单号
     */
    void signForOrder(String orderId);

    /**
     * 查看对应订单号的全部订单项信息
     * @param orderId 订单号
     * @return 返回订单项对象集合
     */
    List<OrderItem> checkOrderItemDetails(String orderId);

    /**
     * 查看全部订单信息
     * @return 返回订单对象集合
     */
    List<Order> checkAllOrders();

    /**
     * 发货
     * @param orderId 订单号
     */
    void sendOrder(String orderId);

    /**
     * 根据订单号获取对应订单的状态值
     * @param orderId 订单号
     * @return 返回状态值
     */
    int getStatusByOrderId(String orderId);
}
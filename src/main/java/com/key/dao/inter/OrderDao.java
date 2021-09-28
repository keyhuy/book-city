package com.key.dao.inter;

import com.key.entity.Order;

import java.util.List;

/**
 * 订单实体dao接口
 *
 * @author Key
 * @date 2021/09/23/21:46
 **/
public interface OrderDao {

    /**
     * 添加新的订单对象
     * @param newOrder 新订单对象
     */
    void addOrder(Order newOrder);

    /**
     * 查询全部订单信息
     * @return 返回订单对象集合
     */
    List<Order> queryOrders();

    /**
     * 根据用户id查询对应的全部订单信息
     * @param userId 用户id
     * @return 返回对应id的订单对象集合
     */
    List<Order> queryOrdersByUserId(int userId);

    /**
     * 更新订单的状态
     * @param orderId 订单号
     * @param newStatus 新的状态信息
     */
    void updateOrderStatus(String orderId, int newStatus);
}
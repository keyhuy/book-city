package com.key.dao.inter;

import com.key.entity.OrderItem;

import java.util.List;

/**
 * 订单商品项实体dao接口
 *
 * @author Key
 * @date 2021/09/23/21:52
 **/
public interface OrderItemDao {

    /**
     * 添加新的订单项对象
     * @param newItem 新的订单项对象
     * @return 返回影响行数
     */
    int addOrderItem(OrderItem newItem);

    /**
     * 根据订单号查询对应的全部订单商品项信息
     * @param orderId 订单号
     * @return 返回订单项对象集合
     */
    List<OrderItem> queryOrderItems(String orderId);
}
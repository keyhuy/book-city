package com.key.dao.impl;

import com.key.dao.BaseDao;
import com.key.dao.inter.OrderDao;
import com.key.entity.Order;

import java.util.List;

/**
 * 订单实体dao实现类
 *
 * @author Key
 * @date 2021/09/23/21:56
 **/
public class OrderDaoImpl extends BaseDao implements OrderDao {

    /**
     * 添加新的订单对象
     * @param newOrder 新订单对象
     */
    @Override
    public void addOrder(Order newOrder) {
        // SQL语句
        String sql = "insert into b_order values(?, ?, ?, ?, ?)";

        updateInfo(sql, newOrder.getOrderId(), newOrder.getOrderTime(),
                newOrder.getOrderPrice(), newOrder.getOrderStatus(), newOrder.getUserId());
    }

    /**
     * 查询全部订单信息
     * @return 返回订单对象集合
     */
    @Override
    public List<Order> queryOrders() {
        // SQL语句
        String sql = "select * from b_order";

        return queryForList(Order.class, sql);
    }

    /**
     * 根据用户id查询对应的全部订单信息
     * @param userId 用户id
     * @return 返回对应id的订单对象集合
     */
    @Override
    public List<Order> queryOrdersByUserId(int userId) {
        // SQL语句
        String sql = "select * from b_order where user_id = ?";

        return queryForList(Order.class, sql, userId);
    }

    /**
     * 更新订单的状态
     * @param orderId 订单号
     * @param newStatus 新的状态信息
     */
    @Override
    public void updateOrderStatus(String orderId, int newStatus) {
        // SQl语句
        String sql = "update b_order set order_status = ? where order_id = ?";

        updateInfo(sql, newStatus, orderId);
    }
}

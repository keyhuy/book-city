package com.key.dao.impl;

import com.key.dao.BaseDao;
import com.key.dao.inter.OrderItemDao;
import com.key.entity.OrderItem;

import java.util.List;

/**
 * 订单商品项实体dao实现类
 *
 * @author Key
 * @date 2021/09/23/22:00
 **/
public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {

    /**
     * 添加新的订单项对象
     * @param newItem 新的订单项对象
     * @return 返回影响行数
     */
    @Override
    public int addOrderItem(OrderItem newItem) {
        // SQL语句
        String sql = "insert into b_order_item values(null, ?, ?, ?, ?, ?)";

        return updateInfo(sql, newItem.getName(), newItem.getCount(),
                newItem.getPrice(), newItem.getTotalPrice(), newItem.getOrderId());
    }

    /**
     * 根据订单号查询对应的全部订单商品项信息
     * @param orderId 订单号
     * @return 返回订单项对象集合
     */
    @Override
    public List<OrderItem> queryOrderItems(String orderId) {
        // SQL语句
        String sql = "select * from b_order_item where order_id = ?";

        return queryForList(OrderItem.class, sql, orderId);
    }
}

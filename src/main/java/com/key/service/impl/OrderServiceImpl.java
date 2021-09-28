package com.key.service.impl;

import com.key.dao.impl.BookDaoImpl;
import com.key.dao.impl.OrderDaoImpl;
import com.key.dao.impl.OrderItemDaoImpl;
import com.key.dao.inter.BookDao;
import com.key.dao.inter.OrderDao;
import com.key.dao.inter.OrderItemDao;
import com.key.entity.*;
import com.key.service.inter.CartService;
import com.key.service.inter.OrderService;

import java.sql.Timestamp;
import java.util.List;

/**
 * 订单模块service实现类
 *
 * @author Key
 * @date 2021/09/23/22:11
 **/
public class OrderServiceImpl implements OrderService {
    private final OrderDao oDao = new OrderDaoImpl();
    private final OrderItemDao oItemDao = new OrderItemDaoImpl();
    private final BookDao bDao = new BookDaoImpl();

    /**
     * 根据购物车信息和用户id生成订单
     * @param cart 购物车对象
     * @param userId 用户id
     * @return 返回订单号
     */
    @Override
    public String createOrder(Cart cart, int userId) {
        // 1. 先创建出唯一的订单号= 时间戳+用户id
        String orderId = System.currentTimeMillis() + userId + "";

        // 2. 根据订单号创建订单对象
        Order newOrder = new Order(orderId, new Timestamp(System.currentTimeMillis()),
                cart.getItemTotalPrice(), 0, userId);

        // 3. 调用dao方法，保存新订单对象
        oDao.addOrder(newOrder);

        // 4. 遍历购物车商品项，将商品项对象一一转化为订单项对象
        for (CartItem cItem : cart.getItems().values()) {
            // 4.1 直接转化成订单项对象
            OrderItem newItem = new OrderItem(cItem.getId(), cItem.getName(), cItem.getCount(),
                    cItem.getPrice(), cItem.getTotalPrice(), orderId);

            // 4.2 调用OrderItemDao存储对象
            oItemDao.addOrderItem(newItem);

            // 4.3 调用bookDao类获取对应id的图书对象
            Book book = bDao.queryBookById(cItem.getId());

            // 4.4 修改图书销量和库存
            book.setSales(book.getSales() + cItem.getCount());
            book.setStock(book.getStock() - cItem.getCount());

            // 4.5 将更新后的图书对象存储回数据库
            bDao.updateBookInfo(book);
        }

        // 5. 生成订单后清空购物车
        CartService cartService = new CartServiceImpl(cart);
        cartService.clearCart();

        return orderId;
    }

    /**
     * 查看我的订单
     * @param userId 用户id
     * @return 返回对应id的订单集合
     */
    @Override
    public List<Order> checkMyOrders(int userId) {
        // 调用dao类方法
        return oDao.queryOrdersByUserId(userId);
    }

    /**
     * 签收订单
     * @param orderId 订单号
     */
    @Override
    public void signForOrder(String orderId) {
        // 直接调用dao类方法
        oDao.updateOrderStatus(orderId, 2);
    }

    /**
     * 查看对应订单号的全部订单项信息
     * @param orderId 订单号
     * @return 返回订单项对象集合
     */
    @Override
    public List<OrderItem> checkOrderItemDetails(String orderId) {
        // 直接调用orderItemDao对应方法
        return oItemDao.queryOrderItems(orderId);
    }

    /**
     * 查看全部订单信息
     * @return 返回订单对象集合
     */
    @Override
    public List<Order> checkAllOrders() {
        // 直接调用dao类获取全部订单对象
        return oDao.queryOrders();
    }

    /**
     * 发货
     * @param orderId 订单号
     */
    @Override
    public void sendOrder(String orderId) {
        // 直接调用dao
        oDao.updateOrderStatus(orderId, 1);
    }

    /**
     * 根据订单号获取对应订单的状态值
     * @param orderId 订单号
     * @return 返回状态值
     */
    @Override
    public int getStatusByOrderId(String orderId) {
        // 1. 调用doa获取全部订单信息
        List<Order> orders = oDao.queryOrders();

        // 2. 遍历订单集合，将对应订单号的状态值返回
        for (Order o : orders) {
            if (orderId.equals(o.getOrderId())) {
                return o.getOrderStatus();
            }
        }

        // 如果没找到对应的订单信息，就返回-1
        return -1;
    }
}

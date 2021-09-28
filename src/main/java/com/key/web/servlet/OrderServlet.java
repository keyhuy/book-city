package com.key.web.servlet;

import com.key.entity.Cart;
import com.key.entity.Order;
import com.key.entity.OrderItem;
import com.key.entity.User;
import com.key.service.impl.OrderServiceImpl;
import com.key.service.inter.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 订单模块servlet
 *
 * @author Key
 * @date 2021/09/23/22:30
 **/
public class OrderServlet extends BaseServlet {
    private final OrderService oService = new OrderServiceImpl();

    /**
     * 生成订单
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 先获取session对象
        HttpSession session = req.getSession();

        // 1. 获取session域中的用户对象
        User loginUser = (User) session.getAttribute("loginUser");

        // 2. 如果用户对象为null
        if (null == loginUser) {
            // 2.1 用户未登录，直接请求转发到登录页面，然后结束当前方法
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return ;
        }

        /* 以下是用户已登录的操作 */

        // 3. 获取session域中的购物车对象
        Cart cart = (Cart) session.getAttribute("cart");
        // 4. 调用service类方法，获取生成的订单号
        String orderId = oService.createOrder(cart, loginUser.getId());

        // 5. 将订单号存储到session域中
        session.setAttribute("orderId", orderId);

        // 6. 重定向到下单成功页面（checkout.jsp）
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }

    /**
     * 查看我的订单
     */
    protected void checkMyOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 先获取session对象
        HttpSession session = req.getSession();

        // 1. 获取session域中的用户对象
        User loginUser = (User) session.getAttribute("loginUser");

        /* 因为 [我的订单] 只有在用户已经登录后才会显示，所以不用判断loginUser是否为null */

        // 2. 根据用户id调用service方法
        List<Order> myOrders = oService.checkMyOrders(loginUser.getId());

        // 3. 将集合存储到request域中
        req.setAttribute("myOrders", myOrders);

        // 4. 最后请求转发到订单页面
        req.getRequestDispatcher("/pages/order/order.jsp").forward(req, resp);
    }

    /**
     * 查看订单详情
     */
    protected void checkOrderItemDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数中的订单号
        String orderId = req.getParameter("orderId");

        // 2. 调用service方法
        List<OrderItem> orderItems = oService.checkOrderItemDetails(orderId);

        // 3. 根据订单号获取对应的订单状态值
        int status = oService.getStatusByOrderId(orderId);

        // 4. 将获取的集合以及订单号存储到request域中
        req.setAttribute("orderItems", orderItems);
        req.setAttribute("orderId", orderId);
        req.setAttribute("status", status);

        // 5. 最后请求转发到订单项详情页
        req.getRequestDispatcher("/pages/order/orderItem.jsp").forward(req, resp);
    }

    /**
     * 确认收货
     */
    protected void signForOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数中的订单号
        String orderId = req.getParameter("orderId");

        // 2. 调用service方法
        oService.signForOrder(orderId);

        // 3. 重定向到订单页面，并刷新
        resp.sendRedirect(req.getContextPath() + "/orderServlet?action=checkMyOrders");
    }

    /**
     * 查看全部订单信息（后台管理）
     */
    protected void checkAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 调用service方法，获取全部订单信息
        List<Order> allOrders = oService.checkAllOrders();

        // 2. 将订单集合存储到request域中
        req.setAttribute("allOrders", allOrders);

        // 3. 最后请求转发到订单管理页面
        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req, resp);
    }

    /**
     * 发货（后台管理）
     */
    protected void sendOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数
        String orderId = req.getParameter("orderId");

        // 2. 调用service方法
        oService.sendOrder(orderId);

        // 3. 重定向到订单管理页面，并刷新
        resp.sendRedirect(req.getContextPath() + "/orderServlet?action=checkAllOrders");
    }
}

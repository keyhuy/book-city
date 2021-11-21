package com.key.web.servlet;

import com.key.entity.Book;
import com.key.entity.Cart;
import com.key.entity.CartItem;
import com.key.service.impl.BookServiceImpl;
import com.key.service.impl.CartServiceImpl;
import com.key.service.inter.BookService;
import com.key.service.inter.CartService;
import com.key.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 购物车模块Servlet
 *
 * @author Key
 * @date 2021/09/20/16:41
 **/
public class CartServlet extends BaseServlet {
    private final BookService bService = new BookServiceImpl();
    private CartService cService;

    /**
     * 将商品加入购物车
     */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数中的id值
        int bookId = WebUtils.parseInt(req.getParameter("id"), 0);

        // 2. 根据获取的id值调用BookService方法，获取对应的图书对象
        Book book = bService.queryBook(bookId);

        // 3. 将图书对象转成商品项对象
        CartItem bookItem = new CartItem(book.getId(), book.getTitle(), 1, book.getPrice(), book.getPrice());

        // 4. 先获取session域中的购物车对象
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // 5. 判断从session中获取的对象是否为null
        if (null == cart) {
            // session中无购物车对象，说明用户还没加购过商品（图书）到车里，需要对购物车对象初始化
            cart = new Cart();

            // 再将购物车对象存储到session域中
            session.setAttribute("cart", cart);
        }

        // 6. 先初始化CartService，再根据该图书对象调用CartService方法，将图书信息加入购物车
        cService = new CartServiceImpl(cart);
        cService.addItem(bookItem);

        // 将刚刚加入购物车的商品项对象名存储到session域中
        session.setAttribute("nameOfLastAddedItem", bookItem.getName());

        // 8. 获取请求头中的Referer的值，作为重定向跳转的路径
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 删除购物车中某件商品
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数中的id值
        int itemId = WebUtils.parseInt(req.getParameter("id"), 0);

        // 2. 获取session域中的购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        // 3. 在购物车对象不为null的情况下
        if (null != cart) {
            // 3.1 初始化service对象
            cService = new CartServiceImpl(cart);

            // 3.2 调用service的对应方法
            cService.deleteItem(itemId);

            // 3.3 最后重定向回到原页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 清空购物车
     */
    protected void clearCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取session域中的购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        // 2. 在购物车对象不为null的情况下
        if (null != cart) {
            // 2.1 初始化cService对象
            cService = new CartServiceImpl(cart);

            // 2.2 调用service对应方法
            cService.clearCart();

            // 2.3 最后重定向回到原页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 修改商品项数量
     */
    protected void updateItemCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数中的id值和更新数量值
        int bookId = WebUtils.parseInt(req.getParameter("id"), 0);
        int updatedCount = WebUtils.parseInt(req.getParameter("updatedCount"), 1);

        // 2. 获取session域中的购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        // 3. 在购物车对象存在的情况下
        if (null != cart) {
            // 3.1 初始化cService对象
            cService = new CartServiceImpl(cart);

            // 3.2 调用service对应功能方法
            cService.updateItemCount(bookId, updatedCount);

            // 3.3 最后重定向回到原页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
}

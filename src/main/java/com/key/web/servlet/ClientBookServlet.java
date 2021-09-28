package com.key.web.servlet;

import com.key.entity.Book;
import com.key.entity.Page;
import com.key.service.impl.BookServiceImpl;
import com.key.service.inter.BookService;
import com.key.utils.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前台图书列表servlet
 *
 * @author Key
 * @date 2021/09/16/17:24
 **/
public class ClientBookServlet extends BaseServlet {

    private final BookService bService = new BookServiceImpl();

    /**
     * 查询每页的图书信息
     */
    protected void pageCliBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数 --> currentPage、pageSize，如果获取不到，就采用默认值1和4
        int currentPage = WebUtil.parseInt(req.getParameter("currentPage"), 1);
        int pageSize = WebUtil.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        // 2. 根据获取的参数调用service方法，获取分页对象
        Page<Book> pageOfBook = bService.pageBooks(currentPage, pageSize);

        // 设置请求路径
        pageOfBook.setReqPath("client/cBookServlet?action=pageCliBooks");

        // 3. 将获取的对象存储到request域中
        req.setAttribute("pageOfBook", pageOfBook);

        // 4. 请求转发到bookCityIndex.jsp
        req.getRequestDispatcher("/pages/client/bookCityIndex.jsp").forward(req, resp);
    }

    /**
     * 根据价格区间查询每页的图书信息
     */
    protected void pageCliBooksByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数
        int currentPage = WebUtil.parseInt(req.getParameter("currentPage"), 1);
        int pageSize = WebUtil.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int minPrice = WebUtil.parseInt(req.getParameter("min"), -1);
        int maxPrice = WebUtil.parseInt(req.getParameter("max"), Integer.MAX_VALUE);

        // 2. 根据获取的参数调用service方法，获取对应的分页对象
        Page<Book> pageOfBook = bService.pageCliBooksByPrice(currentPage, pageSize, minPrice, maxPrice);

        // 判断价格区间是否为null
        StringBuilder sb = new StringBuilder("client/cBookServlet?action=pageCliBooksByPrice");
        if (minPrice != -1) {
            sb.append("&min=").append(req.getParameter("min"));
        }
        if (maxPrice != Integer.MAX_VALUE) {
            sb.append("&max=").append(req.getParameter("max"));
        }

        // 3. 设置分页请求地址
        pageOfBook.setReqPath(sb.toString());

        // 4. 将分页对象存储到request域中
        req.setAttribute("pageOfBook", pageOfBook);

        // 5. 请求转发到bookCityIndex.jsp
        req.getRequestDispatcher("/pages/client/bookCityIndex.jsp").forward(req, resp);
    }
}

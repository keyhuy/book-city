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
import java.util.List;

/**
 * 图书模块Servlet
 *
 * @author Key
 * @date 2021/09/13/19:23
 **/
public class BookServlet extends BaseServlet {
    private final BookService bService = new BookServiceImpl();

    /**
     * 添加图书功能方法
     */
    protected void addBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取隐藏域中的末页页码，转成int型并加一（默认值是0，因为要加1）
        int updatePage = WebUtil.parseInt(req.getParameter("updatePage"), 0) + 1;

        // 1. 获取请求参数，并封装成对象
        Book newBook = WebUtil.injectParamToBean(req.getParameterMap(), new Book());

        // 2. 调用service类方法，将新的图书信息添加到数据库中
        bService.addBook(newBook);

        // 3. 采用重定向跳转回到图书列表页面（/manager/bookServlet?action=queryBooks），注意这里不是直接跳回去book_manager.jsp
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=pageBooks&currentPage=" + updatePage);
    }

    /**
     * 删除图书功能方法
     */
    protected void deleteBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数
        String id = req.getParameter("id");

        // 1.1 将id转成int型，如果转化失败，返回默认值0
        int bookId = WebUtil.parseInt(id, 0);

        // 2. 调用service类方法，实现删除功能
        bService.deleteBook(bookId);

        // 3. 重定向跳转回图书列表页面，并刷新数据
        resp.sendRedirect(req.getContextPath() +
                "/manager/bookServlet?action=pageBooks&currentPage=" +
                req.getParameter("updatePage"));
    }

    /**
     * 更新图书信息
     */
    protected void updateBookInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取表单的内容，并封装成图书对象
        Book newBook = WebUtil.injectParamToBean(req.getParameterMap(), new Book());

        // 2. 调用service方法，将更新后的图书对象存储到数据库中
        bService.updateBookInfo(newBook);

        // 3. 最后重定向跳转到图书列表页面
        resp.sendRedirect(req.getContextPath() +
                "/manager/bookServlet?action=pageBooks&currentPage=" +
                req.getParameter("updatePage"));
    }

    /**
     * 获取图书信息，用于回显到更新页面
     */
    protected void getBookInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取图书id，先转成int型
        int id = WebUtil.parseInt(req.getParameter("id"), 0);

        // 2. 根据id调用service方法，查询对应图书对象
        Book book = bService.queryBook(id);

        // 3. 将该对象存储到request域中
        req.setAttribute("book", book);

        // 4. 最后请求转发到图书更新页面
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req, resp);
    }

    /**
     * 查询所有图书信息
     */
    protected void queryBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 调用service类方法查询所有图书信息
        List<Book> books = bService.queryBooks();

        // 2. 将信息存储到request域中
        req.setAttribute("books", books);

        // 3. 请求转发到图书列表页面（book_manager.jsp）
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }

    /**
     * 查询查询每页的图书信息
     */
    protected void pageBooks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求参数 --> currentPage、pageSize，如果获取不到，就采用默认值1和4
        int currentPage = WebUtil.parseInt(req.getParameter("currentPage"), 1);
        int pageSize = WebUtil.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        // 2. 根据获取的参数调用service方法，获取分页对象
        Page<Book> pageOfBook = bService.pageBooks(currentPage, pageSize);

        // 设置请求路径
        pageOfBook.setReqPath("manager/bookServlet?action=pageBooks");

        // 3. 将获取的对象存储到request域中
        req.setAttribute("pageOfBook", pageOfBook);

        // 4. 请求转发到图书列表页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }
}

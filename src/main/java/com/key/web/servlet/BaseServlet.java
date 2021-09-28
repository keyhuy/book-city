package com.key.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 抽取每个模块的基本操作作为一个抽象方法
 *
 * @author Key
 * @date 2021/09/12/16:00
 **/
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码格式
        req.setCharacterEncoding("utf-8");

        // 获取隐藏域中的请求参数
        String action = req.getParameter("action");

        try {
            // 通过反射获取方法对象，注意这里不能使用getMethod()，因为这里的方法都不是public
            Method userMethod = this.getClass().getDeclaredMethod(
                    action,
                    HttpServletRequest.class,
                    HttpServletResponse.class);

            // 执行对应功能的方法
            userMethod.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            // 获取异常后再抛出
            throw new RuntimeException(e);
        }
    }
}

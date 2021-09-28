package com.key.web.filter;

import com.key.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 管理员权限过滤器
 *
 * @author Key
 * @date 2021/09/26/20:09
 **/
public class ManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 1. 先将req转成HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // 2. 获取session域中的用户对象
        User loginUser = (User) req.getSession().getAttribute("loginUser");

        // 3. 判断用户对象是否为null
        if (null == loginUser) {
            // 为null，表示没登陆，请求转发到登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(servletRequest, servletResponse);
        } else {
            // 不为null，已经登录，放行
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

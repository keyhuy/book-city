package com.key.web.filter;

import com.key.util.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * 事务管理需要用到的过滤器
 *
 * @author Key
 * @date 2021/09/26/22:44
 **/
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 直接给放行方法添加try...catch...语句
        try {
            // 直接放行，相当于执行每一个功能方法
            filterChain.doFilter(servletRequest, servletResponse);

            // 提交事务并释放资源
            JdbcUtils.commitAndClose();
        } catch (IOException | ServletException e) {
            // 出现异常，回滚事务并释放资源
            JdbcUtils.rollbackAndClose();

            e.printStackTrace();

            // 事务回滚后、异常捕获后继续往外抛
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {

    }
}

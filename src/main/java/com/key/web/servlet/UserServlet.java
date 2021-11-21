package com.key.web.servlet;

import com.key.entity.User;
import com.key.service.impl.UserServiceImpl;
import com.key.service.inter.UserService;
import com.key.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * 用户模块的Servlet，包括下面两个功能
 *  - 1. 登录功能 --> login()
 *  - 2. 注册功能 --> register()
 *  - 3. 。。。
 *
 * @author Key
 * @date 2021/09/11/22:34
 **/
public class UserServlet extends BaseServlet {

    private final UserService uService = new UserServiceImpl();

    /**
     * 登录功能
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 通过工具类获取请求参数
        User user = WebUtils.injectParamToBean(req.getParameterMap(), new User());
        String username = user.getUsername();
        String password = user.getPassword();

        // 2. 调用service类方法，判断对应用户名和密码的用户是否存在（不能只判断用户名，因为密码可能不正确）
        User loginUser = uService.login(new User(null, username, password, null));
        if (null == loginUser) {

            // 将错误信息和用户名存到request域中
            req.setAttribute("msg", "用户名或密码错误！");
            req.setAttribute("username", username);

            // 2.1 用户不存在 ——> 转发回到登录页面
            System.out.println("用户不存在，登陆失败！");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {

            // 将登录后的用户信息存储到session域中，用于回显用户名
            req.getSession().setAttribute("loginUser", loginUser);

            // 2.2 用户存在 ——> 调用service类方法，查询出对应的用户对象，并转发到登录成功的页面
            System.out.println(loginUser);
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }

    /**
     * 注销账号
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 销毁存储用户信息的session
        req.getSession().invalidate();

        // 2. 重定向跳转回到首页
        resp.sendRedirect(req.getContextPath());
    }

    /**
     * 注册功能
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session域中的验证码信息
        HttpSession session = req.getSession();
        String verifiedCode = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        // 再删除session域中的信息（必须及时删除）
        session.removeAttribute(KAPTCHA_SESSION_KEY);

        // 1. 获取请求参数
        String code = req.getParameter("code");
        User user = WebUtils.injectParamToBean(req.getParameterMap(), new User());
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();

        // 2. 验证码校验（忽略大小写）
        if (verifiedCode == null || ! verifiedCode.equalsIgnoreCase(code)) {
            // 将验证码错误信息和表单部分内容存到request域中
            req.setAttribute("msg", "验证码不正确！");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            // 2.1. 验证码不正确 ——> 转发回到注册页面
            System.out.println("验证码错误！");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        } else { // 2.2. 验证码正确 ——> 再验证码用户名是否已经存在

            // 3. 调用service类方法验证用户名是否已经存在
            if (uService.isExistsName(username)) {

                // 将验证码错误信息和表单部分内容存到request域中
                req.setAttribute("msg", "该用户名已存在！");
                req.setAttribute("username", username);
                req.setAttribute("email", email);

                // 3.1 用户名已经存在 ——> 注册失败，转发回到注册页面
                System.out.println("用户名已存在！");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                // 3.2 用户名不存在 ——> 注册成功，调用service类方法，保存新的用户信息，并转发到注册成功页面
                uService.register(new User(null, username, password, email));
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        }
    }
}

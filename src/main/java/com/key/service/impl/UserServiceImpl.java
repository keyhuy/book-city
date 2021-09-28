package com.key.service.impl;

import com.key.dao.impl.UserDaoImpl;
import com.key.dao.inter.UserDao;
import com.key.entity.User;
import com.key.service.inter.UserService;

/**
 * UserService实现类
 *  - 主要是三个业务
 *    1. 注册：保存新的用户信息
 *    2. 登录：根据用户名和密码实现登录，返回登录后的用户对象
 *    3. 判断用户名是否已经存在
 *
 * @author Key
 * @date 2021/09/10/19:48
 **/
public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    /**
     * 注册功能
     * @param newUser 新的用户对象
     */
    @Override
    public void register(User newUser) {
        // 保存新的用户信息
        int info = userDao.saveUserInfo(newUser);
        System.out.println(info);
    }

    /**
     * 登录功能
     * @param user 用户对象
     * @return 返回登录后的用户信息
     */
    @Override
    public User login(User user) {
        // 根据用户名和密码查询出对应的用户信息
        return userDao.queryUserByNameAndPwd(user.getUsername(), user.getPassword());
    }

    /**
     * 根据用户名判断用户是否已经存在
     * @param username 用户名
     * @return 如果存在，就返回true，反之返回false
     */
    @Override
    public boolean isExistsName(String username) {
        // 根据用户名查询数据库，如果返回对象为null，则不存在该用户
        return null != userDao.queryUserByName(username);
    }
}

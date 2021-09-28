package com.key.service.inter;

import com.key.entity.User;

/**
 * service接口
 *
 * @author Key
 * @date 2021/09/10/19:44
 **/
public interface UserService {

    /**
     * 注册功能
     * @param newUser 新的用户对象
     */
    void register(User newUser);

    /**
     * 登录功能
     * @param user 用户对象
     * @return 返回登录后的用户信息
     */
    User login(User user);

    /**
     * 根据用户名判断用户是否已经存在
     * @param username 用户名
     * @return 如果存在，就返回true，反之返回false
     */
    boolean isExistsName(String username);
}
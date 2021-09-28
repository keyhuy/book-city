package com.key.dao.inter;

import com.key.entity.User;

/**
 * UserDao接口
 *
 * @author Key
 * @date 2021/09/10/17:15
 **/
public interface UserDao {

    /**
     * 通过姓名查询用户信息
     *  - 用于注册验证，当数据库表中没有该名字时才能验证成功
     * @param username 用户名
     * @return 返回用户对象
     */
    User queryUserByName(String username);

    /**
     * 通过用户名和密码查询用户信息
     *  - 用于登录验证，当数据库表中由该名字和密码时才能验证成功
     * @param username 用户名
     * @param pwd 密码
     * @return 返回用户信息
     */
    User queryUserByNameAndPwd(String username, String pwd);

    /**
     * 保存用户信息
     *  - 用于保存成功注册后的新用户信息
     * @param user 新的用户对象
     * @return 返回影响行数
     */
    int saveUserInfo(User user);
}
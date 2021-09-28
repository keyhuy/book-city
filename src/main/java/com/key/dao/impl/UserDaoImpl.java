package com.key.dao.impl;

import com.key.dao.BaseDao;
import com.key.dao.inter.UserDao;
import com.key.entity.User;

/**
 * 实现UserDao，并继承BaseDao
 *
 * @author Key
 * @date 2021/09/10/17:20
 **/
public class UserDaoImpl extends BaseDao implements UserDao {

    /**
     * 通过姓名查询用户信息
     *  - 用于注册验证，当数据库表中没有该名字时才能验证成功
     * @param username 用户名
     * @return 返回用户对象
     */
    @Override
    public User queryUserByName(String username) {
        // 创建SQL语句
        String sql = "select * from b_user where username = ?";

        return queryForOne(User.class, sql, username);
    }

    /**
     * 通过用户名和密码查询用户信息
     *  - 用于登录验证，当数据库表中由该名字和密码时才能验证成功
     * @param username 用户名
     * @param pwd 密码
     * @return 返回用户信息
     */
    @Override
    public User queryUserByNameAndPwd(String username, String pwd) {
        // 创建SQL语句
        String sql = "select * from b_user where username = ? and password = ?";

        return queryForOne(User.class, sql, username, pwd);
    }

    /**
     * 保存用户信息
     *  - 用于保存成功注册后的新用户信息
     * @param user 新的用户对象
     * @return 返回影响行数
     */
    @Override
    public int saveUserInfo(User user) {
        // 创建SQL语句
        String sql = "insert into b_user values(null, ?, ?, ?)";

        return updateInfo(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
}

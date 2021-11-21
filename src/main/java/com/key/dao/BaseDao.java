package com.key.dao;

import com.key.util.JdbcUtils;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 封装CRUD的基本操作
 *  - 使用DBUtil 工具类库（里面封装了CRUD的基本操作）
 *
 * @author Key
 * @date 2021/09/10/16:40
 **/
public abstract class BaseDao {
    private final QueryRunner queryRunner = new QueryRunner();

    /**
     * 更新数据——增删改
     * @param sql SQL语句
     * @param args 可变参数，对应SQL语句中占位符的值
     * @return 返回更新数据库表后的影响行数，如果查询失败就返回-1
     */
    public int updateInfo(String sql, Object ...args) {

        try {
            // 获取连接对象
            Connection conn = JdbcUtils.getConn();

            // 调用queryRunner的方法
            return queryRunner.update(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            // 异常获取后就抛出
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询单个对象
     * @param clazz 返回对象的类型
     * @param sql SQL语句
     * @param args 可变参数，对应SQL语句中占位符的值
     * @param <T> 返回类型的泛型
     * @return 返回查询到的结果对象
     */
    public <T> T queryForOne(Class<T> clazz, String sql, Object ...args) {
        try {
            // 获取连接对象
            Connection conn  = JdbcUtils.getConn();

            // 调用queryRunner对应方法，并返回结果
            return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
            // 异常获取后就抛出
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询多个对象，返回对象集合
     * @param clazz 返回对象的类型
     * @param sql SQL语句
     * @param args 可变参数，对应SQL语句中占位符的值
     * @param <T> 返回类型的泛型
     * @return 返回查询到的结果对象集合
     */
    public <T>List<T> queryForList(Class<T> clazz, String sql, Object ...args) {
        try {
            // 获取连接对象
            Connection conn = JdbcUtils.getConn();

            // 调用queryRunner对应方法，并返回结果
            return queryRunner.query(conn, sql,
                    new BeanListHandler<>(clazz, new BasicRowProcessor(new GenerousBeanProcessor())), args);
        } catch (SQLException e) {
            e.printStackTrace();
            // 异常获取后就抛出
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询某个值
     * @param sql SQL语句
     * @param args 可变参数，对应SQL语句中占位符的值
     * @return 返回查询的值
     */
    public Object queryForValue(String sql, Object ...args) {
        try {
            // 获取连接对象
            Connection conn = JdbcUtils.getConn();

            // 调用对应方法
            return queryRunner.query(conn, sql, new ScalarHandler<>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
            // 异常获取后就抛出
            throw new RuntimeException(e);
        }
    }
}

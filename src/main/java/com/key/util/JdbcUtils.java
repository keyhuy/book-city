package com.key.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * jdbc 工具类
 *
 * @author Key
 * @date 2021/09/10/15:51
 **/
public class JdbcUtils {
    private static DataSource ds;
    private static final ThreadLocal<Connection> threadLocalOfConn = new ThreadLocal<>();

    static {
        try {
            // 创建属性文件类
            Properties pro = new Properties();
            // 将外部属性文件加载到内存
            pro.load(JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties"));

            // 通过属性类对象获取数据库连接池对象，并将属性文件中各个属性注入连接池
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接对象
     * @return 返回对象
     */
    public static Connection getConn() throws SQLException {
        // 1. 先从threadLocalOfConn中获取连接对象
        Connection conn = threadLocalOfConn.get();

        // 2. 判断连接对象是为null
        if (null == conn) {
            // 2.1 从数据库连接池获取新的连接对象
            conn = ds.getConnection();

            // 2.2 将新的连接对象存储到threadLocal中
            threadLocalOfConn.set(conn);

            // 2.3 直接开启事务（关闭自动提交事务）
            conn.setAutoCommit(false);
        }

        return conn;
    }

    /**
     * 提交事务并归还资源
     */
    public static void commitAndClose() {
        Connection conn = threadLocalOfConn.get();

        if (null != conn) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 提交事务后一定要将treadLocal对象移除，因为tomcat底层使用了线程池技术
        threadLocalOfConn.remove();
    }

    /**
     * 回滚事务并归还资源
     */
    public static void rollbackAndClose() {
        Connection conn = threadLocalOfConn.get();

        if (null != conn) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 回滚事务后一定要将treadLocal对象移除，因为tomcat底层使用了线程池技术
        threadLocalOfConn.remove();
    }
}

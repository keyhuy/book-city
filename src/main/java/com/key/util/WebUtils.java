package com.key.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * web中注入参数工具类
 *
 * @author Key
 * @date 2021/09/12/16:27
 **/
public class WebUtils {

    /**
     * 使用BeanUtils工具类注入参数到bean对象中
     * @param paramMap 参数Map
     * @param bean 需要注入参数（属性值）的对象
     * @param <T> 返回对象泛型
     * @return 返回注入参数后的bean
     */
    public static <T> T injectParamToBean(Map paramMap, T bean) {

        try {
            // 通过BeanUtils 工具类注入参数到Bean中
            BeanUtils.populate(bean, paramMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * 将字符型数据转成int型
     * @param strInt 需要转化的字符型数据
     * @param defaultVal 默认值
     * @return 返回转化后的值
     */
    public static int parseInt(String strInt, int defaultVal) {
        // 如果strInt不是空串不是null，才转成int，如果是空串就直接返回默认值
        if (! "".equals(strInt) && null != strInt) {
            try {
                return Integer.parseInt(strInt);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return defaultVal;
    }

    /**
     * 判断页码是否合法
     * @param currPage 未校验的当前页码
     * @param totalPage 总页码
     * @return 如果页码小于1，就返回1，如果页码大于总页码，就返回总页码
     */
    public static int getLegalPage(int currPage, int totalPage) {
        int legalPage;

        // 如果页码小于1，就设置为1
        legalPage = Math.max(currPage, 1);

        // 如果页码大于总页码，就设置为总页码
        legalPage = currPage > totalPage ? totalPage : legalPage;

        return legalPage;
    }
}

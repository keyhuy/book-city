package com.key.entity;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 *  - itemTotalCount：商品总数
 *  - itemTotalPrice：商品总价
 *  - items：购物车商品集合
 *
 * @author Key
 * @date 2021/09/19/23:09
 **/
public class Cart {
    private Integer itemTotalCount;
    private BigDecimal itemTotalPrice;
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public Cart() {
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public Integer getItemTotalCount() {
        itemTotalCount = 0;

        // 遍历商品项集合，统计每个商品项的数量
        for (CartItem i : items.values()) {
            itemTotalCount += i.getCount();
        }

        return itemTotalCount;
    }

    public BigDecimal getItemTotalPrice() {
        itemTotalPrice = new BigDecimal(0);

        // 遍历集合，统计每个商品项的总价格
        for (CartItem i : items.values()) {
            // 每次调用add()方法累加后再赋值给自己
            itemTotalPrice = itemTotalPrice.add(i.getTotalPrice());
        }

        return itemTotalPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "itemTotalCount=" + itemTotalCount +
                ", itemTotalPrice=" + itemTotalPrice +
                ", items=" + items +
                '}';
    }
}

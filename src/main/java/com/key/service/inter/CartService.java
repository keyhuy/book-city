package com.key.service.inter;

import com.key.entity.CartItem;

import java.math.BigDecimal;

/**
 * 购物车service接口
 *
 * @author Key
 * @date 2021/09/20/15:39
 **/
public interface CartService {

    /**
     * 将商品添加到购物车中
     * @param newItem 新的商品项对象
     */
    void addItem(CartItem newItem);

    /**
     * 将商品从购物车移除
     * @param itemId 商品id
     */
    void deleteItem(int itemId);

    /**
     * 清空购物车，将购物车所有商品移除
     */
    void clearCart();

    /**
     * 修改购物车中商品的数量
     * @param itemId 商品id
     * @param updatedCount 更新后的商品数量
     */
    void updateItemCount(int itemId, int updatedCount);
}
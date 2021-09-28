package com.key.service.impl;

import com.key.entity.Cart;
import com.key.entity.CartItem;
import com.key.service.inter.CartService;

import java.math.BigDecimal;

/**
 * 购物车service实现类
 *
 * @author Key
 * @date 2021/09/20/15:45
 **/
public class CartServiceImpl implements CartService {
    private final Cart cart;

    /**
     * service的构造方法，创建service时将购物车对象传进来
     * @param cart 购物车对象
     */
    public CartServiceImpl(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    /**
     * 将商品添加到购物车中
     * @param newItem 新的商品项对象
     */
    @Override
    public void addItem(CartItem newItem) {
        // 1. 根据新商品对象的id值（购物车中商品对象集合是map集合，id是键）获取对应的商品项对象
        CartItem cartItem = cart.getItems().get(newItem.getId());

        // 2. 根据获取的对象是否为null判断购物车中是否已经存在相同的商品
        if (null == cartItem) {
            // 获取商品为null，说明购物车中无对应id的商品项，直接加入新的商品项对象
            cart.getItems().put(newItem.getId(), newItem);
        } else {
            // 获取的商品项对象不为null，说明购车中已经存在相同的商品项，直接修改商品数量和商品总价即可
            // 商品数量加一
            cartItem.setCount(cartItem.getCount() + 1);

            // 修改商品总价格，根据更新后的商品数量计算出商品总数（商品单价乘以商品数量）
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }
    }

    /**
     * 将商品从购物车移除
     * @param itemId 商品id
     */
    @Override
    public void deleteItem(int itemId) {
        // 根据id值将对应的商品项对象从集合中移除即可
        cart.getItems().remove(itemId);
    }

    /**
     * 清空购物车，将购物车所有商品移除
     */
    @Override
    public void clearCart() {
        // 直接调用map集合中的clear函数即可
        cart.getItems().clear();
    }

    /**
     * 修改购物车中商品的数量
     * @param itemId 商品id
     * @param updatedCount 更新后的商品数量
     */
    @Override
    public void updateItemCount(int itemId, int updatedCount) {
        // 1. 根据id值获取对应的商品项对象
        CartItem item = cart.getItems().get(itemId);

        // 2. 判断获取的对象是否为null，如果不为null，就将对应商品项信息修改
        if (null != item) {
            // 修改商品项数量
            item.setCount(updatedCount);

            // 商品数量影响商品总价格
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }
}

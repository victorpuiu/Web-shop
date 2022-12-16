package com.codecool.shop.dao.implementation.memory;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;


public class CartDaoMem implements CartDao {
    private Cart data;
    private static CartDaoMem instance = null;


    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {
        this.data = cart;
    }

    @Override
    public Cart cartWithSingInUser(int userId) {
        return data;
    }

    @Override
    public Cart createCart(int userId) {
        return null;
    }

    public Cart getCart() {
        return data;
    }

    @Override
    public void payOrder(Cart cart) {
        data.pay();
    }
}

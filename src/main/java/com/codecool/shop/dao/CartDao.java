package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;

public interface CartDao {

    void add(Cart cart);
    Cart cartWithSingInUser(int userId);
    Cart createCart(int userId);
    void payOrder(Cart cart);

}

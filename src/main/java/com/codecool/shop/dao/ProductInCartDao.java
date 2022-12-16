package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;

public interface ProductInCartDao {

    void add(int cartId, Product product);
    void deleteInCartByProductID(int cartId, int productId);
    void deleteByCartID(int cartID);
    List<Product> reviewCart(int cartID);
}

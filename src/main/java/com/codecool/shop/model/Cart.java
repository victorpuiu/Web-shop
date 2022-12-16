package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    int id;
    private int userId;
    private boolean payed = false;

    private List<Product> cart;

    public Cart(int id) {
        this.id = id;
        this.cart = new ArrayList<>();
    }

    public Cart(int id, int userId, boolean payed) {
        this.id = id;
        this.userId = userId;
        this.payed = payed;
    }

    public void add(Product product){
        cart.add(product);
    }

    public List<Product> getCart() {
        return cart;
    }

    public void delete(int productId){
        Product deletedProduct = null;
        for (Product product: cart){
            if (product.getId() == productId){
                deletedProduct = product;
                break;
            }
        }
        cart.remove(deletedProduct);
    }

    public int getUserId() {
        return userId;
    }

    public boolean isPayed() {
        return payed;
    }

    public void pay(){
        cart.clear();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

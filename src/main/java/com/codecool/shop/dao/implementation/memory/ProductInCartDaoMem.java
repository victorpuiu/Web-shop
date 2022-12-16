package com.codecool.shop.dao.implementation.memory;

import com.codecool.shop.dao.ProductInCartDao;
import com.codecool.shop.model.Product;

import java.util.List;

public class ProductInCartDaoMem implements ProductInCartDao {

    private static ProductInCartDaoMem instance = null;
    private CartDaoMem cartDaoMem;

    private ProductInCartDaoMem(CartDaoMem cartDaoMem) {
        this.cartDaoMem = cartDaoMem;
    }

    public static ProductInCartDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductInCartDaoMem(CartDaoMem.getInstance());
        }
        return instance;
    }

    @Override
    public void add(int cartId, Product product) {
        cartDaoMem.getCart().add(product);
    }

    @Override
    public void deleteInCartByProductID(int cartId, int productId) {
        cartDaoMem.getCart().delete(productId);
    }

    @Override
    public void deleteByCartID(int cartID) {

    }

    @Override
    public List<Product> reviewCart(int cartID) {
        return cartDaoMem.getCart().getCart();
    }
}

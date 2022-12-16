package com.codecool.shop.service;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductInCartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;

import java.util.List;

public class CartService {
    private CartDao cartDao;
    private ProductInCartDao productInCartDao;

    public CartService(CartDao cartDao, ProductInCartDao productInCartDao) {
        this.cartDao = cartDao;
        this.productInCartDao = productInCartDao;
    }


    public List<Product> getProductInCart(Customer customer) {
        Cart cart = cartDao.cartWithSingInUser(customer.getId());
        int cardId = 0;
        if (cart != null) {
            cardId = cart.getId();
        }
        return productInCartDao.reviewCart(cardId);
    }


    public void addToCart(Customer customer, Product product) {
        Cart cart = cartDao.cartWithSingInUser(customer.getId());
        productInCartDao.add(cart.getId(), product);
    }

    public void deleteFromCart(Customer customer, int productId) {
        Cart cart = cartDao.cartWithSingInUser(customer.getId());
        productInCartDao.deleteInCartByProductID(cart.getId(), productId);
    }

    public void payOrder(Customer customer){
        Cart cart = cartDao.cartWithSingInUser(customer.getId());
        cartDao.payOrder(cart);
    }

    public String sumPrice(Customer customer) {
        int sum = 0;
        String slicedPrice = "";
        for (Product product : getProductInCart(customer)) {
            String strPrice = product.getPrice();
            slicedPrice = strPrice.substring(0, strPrice.length() - 4);
            sum += Integer.parseInt(slicedPrice);
        }
        return String.valueOf(sum) + " USD";
    }

}

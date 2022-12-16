package com.codecool.shop.dao;

import com.codecool.shop.model.Customer;

public interface CustomerDao {

    void add(Customer customer);
    Customer find(int id);
    Customer findByEmail(String email);
}

package com.codecool.shop.service;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;

public class CustomerService {
    private CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public boolean registration(Customer customer) {
        if (getCostumerByEmail(customer.getEmail()) == null) {
            customerDao.add(customer);
            return true;
        } else {
            return false;
        }
    }

    public Customer getCostumerByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public boolean loginSuccess(Customer customer) {
        if (getCostumerByEmail(customer.getEmail()) != null){
            if (getCostumerByEmail(customer.getEmail()).getPassword().equals(customer.getPassword())) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }

    }
}

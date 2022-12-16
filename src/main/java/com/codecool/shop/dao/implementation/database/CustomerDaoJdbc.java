package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;

import javax.sql.DataSource;
import java.sql.*;

public class CustomerDaoJdbc implements CustomerDao {

    private DataSource dataSource;

    public CustomerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Customer customer) {
        try (Connection con = dataSource.getConnection()) {
            String query = "INSERT INTO customer (name, email, password, address, city, state, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, customer.getName());
            st.setString(2, customer.getEmail());
            st.setString(3, customer.getPassword());
            st.setString(4, customer.getAddress());
            st.setString(5, customer.getCity());
            st.setString(6, customer.getState());
            st.setString(7, customer.getZipCode());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Customer find(int id) {
        return null;
    }

    @Override
    public Customer findByEmail(String gotEmail) {
        try (Connection con = dataSource.getConnection()) {
            String query = "SELECT id, name, email, password, address, city, state, zip_code FROM customer WHERE email = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, gotEmail);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            String password = rs.getString(4);
            String address = rs.getString(5);
            String city = rs.getString(6);
            String state = rs.getString(7);
            String zipCode = rs.getString(8);

            Customer customer = new Customer(name, email, password);
            customer.setId(id);
            customer.setAddress(address);
            customer.setCity(city);
            customer.setState(state);
            customer.setZipCode(zipCode);
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

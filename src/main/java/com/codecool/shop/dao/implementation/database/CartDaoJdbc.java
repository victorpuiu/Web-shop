package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJdbc implements CartDao {
    private DataSource dataSource;

    public CartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Cart cart) {

    }


    @Override
    public Cart cartWithSingInUser(int searchedUserId) {
        try (Connection con = dataSource.getConnection()) {
            String query = "SELECT id, user_id, payed  FROM cart WHERE user_id = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, searchedUserId);

            ResultSet rs = st.executeQuery();
            List<Cart> carts = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                int userId = rs.getInt(2);
                boolean payed = rs.getBoolean(3);
                Cart cart = new Cart(id, userId, payed);
                carts.add(cart);
            }
            for (Cart cart : carts) {
                if (!cart.isPayed()) {
                    return cart;
                }
            }
            return createCart(searchedUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart createCart(int userId) {
        try (Connection con = dataSource.getConnection()) {
            String query = "INSERT INTO cart (user_id, payed) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userId);
            st.setBoolean(2, false);

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            Cart cart = new Cart(id, userId, false);
            return cart;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void payOrder(Cart cart) {
        try (Connection con = dataSource.getConnection()) {
            String query = "UPDATE cart  SET payed = true WHERE id = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, cart.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

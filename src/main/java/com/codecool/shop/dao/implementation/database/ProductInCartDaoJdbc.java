package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductInCartDao;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInCartDaoJdbc implements ProductInCartDao {
    private DataSource dataSource;

    public ProductInCartDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(int cartId, Product product) {
        try (Connection con = dataSource.getConnection()) {
            String query = "INSERT INTO product_in_cart (cart_id, product_id) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, cartId);
            st.setInt(2, product.getId());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteInCartByProductID(int cartId, int productId) {
        try (Connection con = dataSource.getConnection()) {
            String query = "DELETE FROM product_in_cart\n" +
                    "WHERE id = (SELECT id\n" +
                    "            FROM product_in_cart\n" +
                    "            WHERE cart_id = ? AND product_id = ?\n" +
                    "            LIMIT 1)";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, cartId);
            st.setInt(2, productId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteByCartID(int cartId) {


    }

    @Override
    public List<Product> reviewCart(int cartId) {
        try (Connection con = dataSource.getConnection()) {
            String query = "SELECT product_id FROM product_in_cart WHERE cart_id = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, cartId);

            ResultSet rs = st.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                ProductDaoJdbc productDaoJdbc = new ProductDaoJdbc(dataSource);
                Product product = productDaoJdbc.find(rs.getInt(1));
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

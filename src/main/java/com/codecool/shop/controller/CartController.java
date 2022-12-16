package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.DaoRepository;
import com.codecool.shop.model.Customer;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.CustomerService;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        DaoRepository daoRepository = DaoRepository.getInstance();

        ProductDao productDataStore = daoRepository.getProductDao();
        ProductCategoryDao productCategoryDataStore = daoRepository.getProductCategoryDao();
        SupplierDao supplierDataStore = daoRepository.getSupplierDao();
        CartDao cartDataStore = daoRepository.getCartDao();

        CustomerDao customerDao = daoRepository.getCustomerDao();

        ProductInCartDao productInCartDataStore = daoRepository.getProductInCartDao();

        ProductService productService = new ProductService(productDataStore, productCategoryDataStore, supplierDataStore);
        CartService cartService = new CartService(cartDataStore, productInCartDataStore);

        CustomerService customerService = new CustomerService(customerDao);
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user");
        Customer customer = customerService.getCostumerByEmail(userEmail);

        String productID = request.getParameter("id");
        cartService.addToCart(customer, productService.getProductDaoById(Integer.parseInt(productID)));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            DaoRepository daoRepository = DaoRepository.getInstance();

            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");

            CartDao cartDataStore = daoRepository.getCartDao();
            ProductInCartDao productInCartDataStore = daoRepository.getProductInCartDao();

            CustomerDao customerDataStore = daoRepository.getCustomerDao();

            CartService cartService = new CartService(cartDataStore, productInCartDataStore);

            CustomerService customerService = new CustomerService(customerDataStore);
            String userEmail = (String) session.getAttribute("user");
            Customer customer = customerService.getCostumerByEmail(userEmail);

            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("products", cartService.getProductInCart(customer));
            context.setVariable("price", cartService.sumPrice(customer));
            engine.process("product/cart.html", context, resp.getWriter());
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoRepository daoRepository = DaoRepository.getInstance();
        CartDao cartDataStore = daoRepository.getCartDao();
        ProductInCartDao productInCartDataStore = daoRepository.getProductInCartDao();

        CustomerDao customerDataStore = daoRepository.getCustomerDao();
        CustomerService customerService = new CustomerService(customerDataStore);
        HttpSession session = req.getSession();

        String userEmail = (String) session.getAttribute("user");
        Customer customer = customerService.getCostumerByEmail(userEmail);
        CartService cartService = new CartService(cartDataStore, productInCartDataStore);
        String productID = req.getParameter("id");
        cartService.deleteFromCart(customer, Integer.parseInt(productID));
    }
}












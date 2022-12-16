package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.ProductInCartDao;
import com.codecool.shop.dao.implementation.DaoRepository;
import com.codecool.shop.model.Customer;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.CustomerService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        DaoRepository daoRepository = DaoRepository.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        CartDao cartDataStore = daoRepository.getCartDao();
        CustomerDao customerDao = daoRepository.getCustomerDao();
        ProductInCartDao productInCartDataStore = daoRepository.getProductInCartDao();

        CustomerService customerService = new CustomerService(customerDao);
        CartService cartService = new CartService(cartDataStore, productInCartDataStore);

        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = req.getSession();

        String userEmail = (String) session.getAttribute("user");
        Customer customer = customerService.getCostumerByEmail(userEmail);

        context.setVariable("products", cartService.getProductInCart(customer));
        context.setVariable("price", cartService.sumPrice(customer));
        engine.process("checkout.html", context, resp.getWriter());
    }
}

package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.ProductInCartDao;
import com.codecool.shop.dao.implementation.DaoRepository;
import com.codecool.shop.emailSender.EmailSender;
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


@WebServlet("/payment")
public class PaymentController extends HttpServlet {

    Customer customer;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoRepository daoRepository = DaoRepository.getInstance();
        CartDao cartDataStore = daoRepository.getCartDao();
        ProductInCartDao productInCartDataStore = daoRepository.getProductInCartDao();
        CartService cartService = new CartService(cartDataStore, productInCartDataStore);

        EmailSender sender = new EmailSender();
        CustomerDao customerDao = daoRepository.getCustomerDao();
        CustomerService customerService = new CustomerService(customerDao);
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user");
        Customer user = customerService.getCostumerByEmail(userEmail);

        cartService.payOrder(user);

        String emailAddress = customer.getEmail();
        String emailContent = "<div>Dear " + customer.getName() + "</div>" +
                "<div>Your order will be arrive shortly" + "\n" + "</div>" +
                "<div>Total price: " + cartService.sumPrice(user) + "\n" + "</div>" +
                "<div>Address:</div>" +
                "<div>" + customer.getState() + "\n" + "</div>" +
                "<div>" + customer.getZipCode() + "\n" + "</div>" +
                "<div>" + customer.getCity() + "\n" + "</div>" +
                "<div>" + customer.getAddress() + "</div>";
        sender.sendEmail(emailAddress, emailContent);
        response.sendRedirect(request.getContextPath() + "/successful-order");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        DaoRepository daoRepository = DaoRepository.getInstance();
        CartDao cartDataStore = daoRepository.getCartDao();
        ProductInCartDao productInCartDataStore = daoRepository.getProductInCartDao();
        CartService cartService = new CartService(cartDataStore, productInCartDataStore);
        customer = new Customer(request.getParameter("name"), request.getParameter("email"),
                request.getParameter("address"), request.getParameter("city"), request.getParameter("state"),
                request.getParameter("zip"));
        CustomerDao customerDao = daoRepository.getCustomerDao();
        CustomerService customerService = new CustomerService(customerDao);
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user");
        Customer user = customerService.getCostumerByEmail(userEmail);
        context.setVariable("cart", cartService.getProductInCart(user));
        context.setVariable("totalPrice", cartService.sumPrice(user));
        engine.process("payment.html", context, response.getWriter());
    }
}
